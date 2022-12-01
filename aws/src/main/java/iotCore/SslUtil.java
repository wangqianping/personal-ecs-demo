package iotCore;

import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;

/**
 * SSL 工具类
 */
public class SslUtil {


    private static Logger logger = LoggerFactory.getLogger(SslUtil.class);

    /**
     * 利用开源的工具类解析openssl私钥，openssl私钥文件格式为pem，需要去除页眉页脚后才能被java读取
     *
     * @param file
     * @return
     */
    public static PrivateKey getPrivateKey(File file) {
        if (file == null) {
            return null;
        }
        PrivateKey privKey = null;
        PemReader pemReader = null;
        try {
            pemReader = new PemReader(new FileReader(file));
            PemObject pemObject = pemReader.readPemObject();
            byte[] pemContent = pemObject.getContent();
            //支持从PKCS#1或PKCS#8 格式的私钥文件中提取私钥
            if (pemObject.getType().endsWith("RSA PRIVATE KEY")) {
                /*
                 * 取得私钥  for PKCS#1
                 * openssl genrsa 默认生成的私钥就是PKCS1的编码
                 */
                RSAPrivateKey asn1PrivKey = RSAPrivateKey.getInstance(pemContent);
                RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
                KeyFactory keyFactory = KeyFactory.getInstance("rsa");
                privKey = keyFactory.generatePrivate(rsaPrivKeySpec);
            } else if (pemObject.getType().endsWith("PRIVATE KEY")) {
                /*
                 * 通过openssl pkcs8 -topk8转换为pkcs8，例如（-nocrypt不做额外加密操作）：
                 * openssl pkcs8 -topk8 -in pri.key -out pri8.key -nocrypt
                 *
                 * 取得私钥 for PKCS#8
                 */
                PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(pemContent);
                KeyFactory kf = KeyFactory.getInstance("rsa");
                privKey = kf.generatePrivate(privKeySpec);
            }
        } catch (FileNotFoundException e) {
            logger.error("read private key fail,the reason is the file not exist");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("read private key fail,the reason is :" + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            logger.error("read private key fail,the reason is :" + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.error("read private key fail,the reason is :" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pemReader != null) {
                    pemReader.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return privKey;
    }

    /**
     * 获取SSLContext，基于CA， Certificate， key及密码进行SSL上下文的创建
     *
     * @param caPath
     * @param crtPath
     * @param keyPath
     * @param password
     * @return
     * @throws Exception
     */
    public static SSLContext getSSLContext(String caPath, String crtPath, String keyPath, String password) throws Exception {
        /*
         * CA证书是用来认证服务端的，这里的CA就是一个公认的认证证书
         * TrustManagerFactory 管理的是授信的CA证书，所以KeyStore里面存放的不需要私钥信息，通常也不可能有
         */
        CertificateFactory cAf = CertificateFactory.getInstance("X.509");
        FileInputStream caIn = new FileInputStream(caPath);
        X509Certificate ca = (X509Certificate) cAf.generateCertificate(caIn);
        KeyStore caKs = KeyStore.getInstance("JKS");
        caKs.load(null, password.toCharArray());
        caKs.setCertificateEntry("ca1", ca); //可以通过设置alias不同，配置多个ca实例，即配置多个可信的root CA。
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
        tmf.init(caKs);
        caIn.close();

        //这个客户端证书，是用来发送给服务端的，准备做双向验证用的。
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream crtIn = new FileInputStream(crtPath);
        X509Certificate caCert = (X509Certificate) cf.generateCertificate(crtIn);
        crtIn.close();

        //客户端私钥，是用来处理双向SSL验证中服务端用客户端证书加密的数据的解密（解析签名）工具
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, password.toCharArray());
        ks.setCertificateEntry("certificate3", caCert);

        FileInputStream crtIn2 = new FileInputStream(crtPath);
        X509Certificate caCert1 = (X509Certificate) cf.generateCertificate(crtIn2);
        crtIn2.close();
        ks.setCertificateEntry("certificate1", caCert1);

        PrivateKey privateKey = getPrivateKey(new File(keyPath));
        /*
         * 注意：下面这行代码中非常重要的一点是：
         * setKeyEntry这个函数的第二个参数 password，他不是指私钥的加密密码，只是KeyStore对这个私钥进行管理设置的密码
         *
         * setKeyEntry中最后一个参数，chain的顺序是证书链中越靠近当前privateKey节点的证书，越靠近数字下标0的位置。即chain[0]是privateKey对应的证书，
         * chain[1]是签发chain[0]的证书，以此类推，有chain[i+1]签发chain[i]的关系。
         */
        ks.setKeyEntry("private-key", privateKey, password.toCharArray(), new Certificate[]{caCert, caCert1});

        /*
         * KeyManagerFactory必须是证书和私钥配对使用，即KeyStore里面装载客户端证书以及对应的私钥，双向SSL验证需要。
         */
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
        kmf.init(ks, password.toCharArray());

        /*
         * 最后创建SSL套接字工厂 SSLSocketFactory
         * 注意：这里，SSLContext不支持TLSv2创建
         */
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        KeyManager[] kms = kmf.getKeyManagers();

        TrustManager[] tms = tmf.getTrustManagers();
        context.init(kms, tms, new SecureRandom());
        return context;
    }

    public static SSLSocketFactory getSSLSocketFactory(String caPath, String crtPath, String keyPath, String password) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        KeyManagerFactory keyManagerFactory = createAndInitKeyManagerFactory(crtPath, keyPath, password);
        TrustManagerFactory trustManagerFactory = createAndInitTrustManagerFactory(caPath, password);

        SslContext sslContext = SslContextBuilder.forClient()
                .keyManager(keyManagerFactory)
                .trustManager(trustManagerFactory)
                .clientAuth(ClientAuth.REQUIRE)
                .build();

        // finally,create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");

        context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        return context.getSocketFactory();
    }

    private static KeyManagerFactory createAndInitKeyManagerFactory(String certFile, String keyFile, String password) throws Exception {
        String cert = getPem(certFile);
        String key = getPem(keyFile);
        X509Certificate certHolder = readCertFile(cert);
        Object keyObject = readPrivateKeyFile(key);
        JcaPEMKeyConverter keyConverter = new JcaPEMKeyConverter().setProvider("BC");

        PrivateKey privateKey;
        if (keyObject instanceof PEMEncryptedKeyPair) {
            PEMDecryptorProvider provider = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
            KeyPair keyPair = keyConverter.getKeyPair(((PEMEncryptedKeyPair) keyObject).decryptKeyPair(provider));
            privateKey = keyPair.getPrivate();
        } else if (keyObject instanceof PEMKeyPair) {
            KeyPair keyPair = keyConverter.getKeyPair((PEMKeyPair) keyObject);
            privateKey = keyPair.getPrivate();
        } else if (keyObject instanceof PrivateKey) {
            privateKey = (PrivateKey) keyObject;
        } else {
            throw new RuntimeException("Unable to get private key from object: " + keyObject.getClass());
        }

        KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        clientKeyStore.load(null, null);
        clientKeyStore.setCertificateEntry("cert", certHolder);
        clientKeyStore.setKeyEntry("private-key", privateKey, password.toCharArray(), new Certificate[]{certHolder});

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(clientKeyStore, password.toCharArray());
        return keyManagerFactory;
    }

    private static TrustManagerFactory createAndInitTrustManagerFactory(String caCertFile, String password) throws Exception {
        X509Certificate caCertHolder;
        String caCert = getPem(caCertFile);
        caCertHolder = readCertFile(caCert);

        KeyStore caKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        caKeyStore.load(null, password.toCharArray());
        caKeyStore.setCertificateEntry("caCert-cert", caCertHolder);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(caKeyStore);
        return trustManagerFactory;
    }

    private static X509Certificate readCertFile(String fileContent) throws Exception {
        X509Certificate certificate = null;
        if (fileContent != null && !fileContent.trim().isEmpty()) {
            fileContent = fileContent.replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.decodeBase64(fileContent);
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            certificate = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(decoded));
        }

        return certificate;
    }

    private static PrivateKey readPrivateKeyFile(String fileContent) throws Exception {
        java.security.interfaces.RSAPrivateKey privateKey = null;
        if (fileContent != null && !fileContent.isEmpty()) {
            fileContent = fileContent.replaceAll(".*BEGIN.*PRIVATE KEY.*", "")
                    .replaceAll(".*END.*PRIVATE KEY.*", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.decodeBase64(fileContent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = (java.security.interfaces.RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
        }
        return privateKey;
    }


    public static PrivateKey getPrivateKey(String path) throws Exception {
        Base64 base64 = new Base64();
        byte[] buffer = base64.decode(getPem(path));

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static String getPem(String path) throws Exception {
        System.out.println(path);
        FileInputStream fin = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fin));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine == "" || readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }
        fin.close();
        return sb.toString();
    }

    /**
     * 读取证书
     *
     * @param caCrtFile
     * @param crtFile
     * @param keyFile
     * @param password
     * @return
     * @throws Exception
     */
    public static SSLSocketFactory getSocketFactory(final String caCrtFile, final String crtFile, final String keyFile,
                                                    final String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        PEMReader reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
        X509Certificate caCert = (X509Certificate) reader.readObject();
        reader.close();

        // load client certificate
        reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtFile)))));
        X509Certificate cert = (X509Certificate) reader.readObject();
        reader.close();

        // load client private key
        reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(keyFile)))));
        KeyPair key = (KeyPair) reader.readObject();
        reader.close();

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        // client key and certificates are sent to server so it can authenticate us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}