package com.lotus.core.base.basefile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lotus.core.base.basedate.BaseDateUtil;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.base.returnmessage.IMessage;
import com.lotus.core.base.returnmessage.Message;

@SpringBootConfiguration
public class BaseFileUpDown extends AbstractLogger implements FactoryBean<BaseFileUpDown>{

	@Value("${lotus.defaultUploadPath}")
	private String defaultUploadPath;
	
	@Value("${lotus.chkdsk}")
	private String chkdsk;
	
	@Autowired
	private IMessage iMessage;
	
	@Value("${lotus.server}")
	private String server;
	
	
	
	public Message upload(MultipartFile[] files,String uploadPath){
		Message message = null;
		List<String> paths = new ArrayList<>(files.length);
		File dest = null;
		try {
			message = iMessage.buildDefaultMessage();
			//String path = ResourceUtils.getURL("classpath:").getPath()+File.separator+uploadPath;
			
			StringBuffer sb = new StringBuffer(this.defaultUploadPath);
			if(!StringUtils.isEmpty(uploadPath)) {
				sb.append(File.separator);
				sb.append(uploadPath);
				sb.append(File.separator);
			}
			sb.append(BaseDateUtil.Instance().obtainymd());
			this.logger.info("文件上传路径是{}",sb.toString());
			dest = new File(this.chkdsk+sb.toString());
			if(!dest.exists()) {
				dest.mkdirs();
				dest = null;
			}
			
			for(MultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				String uploadPathDest =this.chkdsk + sb.toString()+File.separator+fileName;
				this.logger.info("文件上传整路径是{}",uploadPathDest);
				
				dest = new File(uploadPathDest);
				file.transferTo(dest);
				
				paths.add(sb.toString()+File.separator+fileName);
				/*
				if("1".equals(this.server)) {
					paths.add(uploadPathDest.substring(sb.indexOf(":")+1));
				}else {
					paths.add(sb.toString()+File.separator+fileName);
				}*/
				
				dest = null;
			}
			message = iMessage.buildSuccessMessage();
			message.setData(paths);
			
		} catch (IllegalStateException | IOException e) {
				message = iMessage.buildFileFailMessage();
				message.setCause(e.getCause());
				message.setRealMessage(e.getMessage());
	
			this.logger.error(e.getMessage());
		}
		
		return message;
		
	}

	@Override
	protected Class<?> getClassForLogger() {
		return BaseFileUpDown.class;
	}

	@Override
	public BaseFileUpDown getObject() throws Exception {
		
		return this;
	}

	@Override
	public Class<?> getObjectType() {
		
		return getClassForLogger();
	}
}
