package com.sb.eaccBatch.config;


import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sb.ec.lib.P;

@Configuration
public class JasyptConfig {

public final static String KEY = "knight76";
public final static String ALGORITHM = "PBEWithMD5AndDES";

		@Bean("jasyptStringEncryptor")
		public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(KEY);
		config.setAlgorithm(ALGORITHM);
		config.setKeyObtentionIterations("1000"); // Sets the number of hashing iterations applied to obtain the encryption key.
		config.setPoolSize("1"); // Sets the size of the pool of encryptors to be created.
		config.setProviderName("SunJCE"); //  Sets the security provider to be used for obtaining the encryption algorithm.
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		return encryptor;
		}

}

