package com.lotus.backstage.file;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;

@RestController
@RequestMapping("file")
public class FileController extends BaseController<FileController>{
	
	/**
	 * @param uploadNewsImg
	 * @param uploadPath
	 * @return
	 */
	@RequestMapping("uploadFiles")
	public Message uploaFiles(MultipartFile[] uploadNewsImg,@RequestParam("uploadPath") String uploadPath){
		this.logger.info("----------------上传文件开始-----------------");
		this.logger.info("参数是uploadPath={},fileLength={}",uploadPath,uploadNewsImg.length);
		Message message = null;
		if(uploadNewsImg.length==0) {
			message=iMessage.buildControllerParameterEmptyMessage();
			message.setAlertMessage("文件大小为0");
			return message;
		}
		message = this.baseFileUpDown.upload(uploadNewsImg,uploadPath);
		this.logger.info("----------------上传文件结束-----------------");
		this.logger.info("结果是{}",message);
		return message;	
	}
	
	/**
	  * 公用
	 * @param uploadPath
	 * @param request
	 * @return
	 */
	@RequestMapping("commonUploadFiles")
	public Message uploaFile(@RequestParam("uploadPath") String uploadPath,HttpServletRequest request){
		this.logger.info("----------------上传文件开始-----------------");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;

		Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
		
		this.logger.info("参数是uploadPath={},fileLength={}",uploadPath,fileMap.size());
		
		Message message = null;
		if(fileMap.size()==0) {
			message=iMessage.buildControllerParameterEmptyMessage();
			message.setAlertMessage("文件大小为0");
			return message;
		}
		
		Collection<MultipartFile> multipartFileCollection = fileMap.values();
		
		MultipartFile[] multipartFileArray = {};
		multipartFileArray = multipartFileCollection.toArray(multipartFileArray);
		
		message = this.baseFileUpDown.upload(multipartFileArray,uploadPath);
		this.logger.info("----------------上传文件结束-----------------");
		this.logger.info("结果是{}",message);
		return message;	
	}
}
