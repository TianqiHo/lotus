package com.lotus.wx.minipro;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.core.base.baselogger.AbstractLogger;

@Component
public class WxMiniProDecodeUserInfo extends AbstractLogger{
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected Class<?> getClassForLogger() {
		return WxMiniProDecodeUserInfo.class;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getUserInfo(String encryptedData,String sessionKey,String iv)throws Exception{
	    // 被加密的数据
	    byte[] dataByte = Base64.getDecoder().decode(encryptedData);
	    // 加密秘钥
	    byte[] keyByte = Base64.getDecoder().decode(sessionKey);
	    // 偏移量
	    byte[] ivByte = Base64.getDecoder().decode(iv);
	    
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Map<String,Object> result = null;
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init( Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String resultStr = new String(resultByte, "UTF-8");
            result = objectMapper.readValue(resultStr, Map.class);
        }
	    return result;
	}
}
