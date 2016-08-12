/*
 * @项目名称: JavaMail
 * @文件名称: MailUtil.java
 * @日期: 2016-6-12 上午08:32:38  
 * @版权: 2011 河南中审科技有限公司
 * @开发公司或单位：河南中审科技有限公司研发部
 */
package com.king.javamail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;



/**    
 * 项目名称：JavaMail   <br/>
 * 类名称：MailUtil.java   <br/>
 * 类描述：   <br/>
 * 创建人：开发部笔记本   <br/>
 * 创建时间：2016-6-12 上午08:32:39   <br/>
 * 修改人：开发部笔记本   <br/>
 * 修改时间：2016-6-12 上午08:32:39   <br/>
 * 修改备注：    <br/>
 * @version  1.0  
 */
public class MailUtil {

	public static void main(String[] args) throws MessagingException, FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.163.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		//使用javaMail发送邮件的5个步骤
		//1、创建session
		Session session = Session.getInstance(prop);
		//开启session的debug模式，这样可有看到程序发送Email的运行状态。
		session.setDebug(true);
		//2、通过session得到transport对象
		Transport ts = session.getTransport();
		//3、使用邮箱的用户名和密码脸上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都要通过验证后，
		ts.connect("smtp.163.com", "hnzswqc","qq529597089");
		//才能正常发送邮件给收件人。
		Message msg = createAttachMail(session);
		ts.sendMessage(msg, msg.getAllRecipients());
		ts.close();
		System.out.println("发送完成");
	}
	
	/**
	 * 
	 * 方法描述：一般邮件信息<br/>
	 * 创建人：开发部笔记本   <br/>
	 * 创建时间：2016-6-12 上午11:56:06<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static MimeMessage createSimpleMail(Session session) throws AddressException, MessagingException, FileNotFoundException, IOException{
		//创建邮件对象
		MimeMessage message = new MimeMessage(session);
		//指明邮件的发送人
		message.setFrom(new InternetAddress("hnzswqc@163.com"));
		//指明邮件的收件人。
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("hnzswqc@163.com"));
		//邮件的标题
		message.setSubject("测试简单的邮件信息标题，只包含文本信息");
		message.setContent("你好啊！这封邮件时通过程序发送的。","text/html;charset=UTF-8");
		//message.writeTo(new FileOutputStream("E:\\txt.eml"));
		return message;
	}
	
	/**
	 * 
	 * 方法描述：含图片邮件信息<br/>
	 * 创建人：开发部笔记本   <br/>
	 * 创建时间：2016-6-12 下午01:58:11<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public  static MimeMessage createImageMail(Session session) throws AddressException, MessagingException{
		//创建邮件对象
		MimeMessage message = new MimeMessage(session);
		//指明邮件的发送人
		message.setFrom(new InternetAddress("hnzswqc@163.com"));
		//指明邮件的收件人。
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("hnzswqc@163.com"));
		//邮件的标题
		message.setSubject("测试简单的邮件信息标题，只包含文本信息");
		//内容
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("你好啊！这封邮件时通过程序发送的。带图片的。<img src='cid:xxx.png'>","text/html;charset=UTF-8");
		
		//准备图片的数据
		MimeBodyPart image = new MimeBodyPart();
		DataHandler db = new DataHandler(new FileDataSource("c:\\1.png"));
		image.setDataHandler(db);
		image.setContentID("xxx.png");
		
		//描述数据信息
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(image);
		mm.setSubType("related");
		message.setContent(mm);
		message.saveChanges();
		return message;
	}
	
	/**
	 * 
	 * 方法描述：带内容、图片、附件的邮件信息<br/>
	 * 创建人：开发部笔记本   <br/>
	 * 创建时间：2016-6-12 下午02:13:07<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static MimeMessage createAttachMail(Session session) throws AddressException, MessagingException, UnsupportedEncodingException{
		//创建邮件对象
		MimeMessage message = new MimeMessage(session);
		//指明邮件的发送人
		message.setFrom(new InternetAddress("hnzswqc@163.com"));
		//指明邮件的收件人。
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("hnzswqc@163.com"));
		//邮件的标题
		message.setSubject("测试简单的邮件信息标题，只包含文本信息");
		//内容
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("你好啊！这封邮件时通过程序发送的。带图片和附件的。<img src='cid:xxx.png'>","text/html;charset=UTF-8");
		//准备图片的数据
		MimeBodyPart image = new MimeBodyPart();
		DataHandler db = new DataHandler(new FileDataSource("c:\\1.png"));
		image.setDataHandler(db);
		image.setContentID("xxx.png");
		
		//描述数据信息
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(image);
		mm.setSubType("related");
		
		
		
		//创建邮件附件
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("c:\\1.doc"));
		attach.setDataHandler(dh);
		attach.setFileName(MimeUtility.encodeText(dh.getName()));
		
		//附件
		MimeMultipart mp2= new MimeMultipart();
		mp2.addBodyPart(attach);
		
		//正文
		MimeBodyPart content = new MimeBodyPart();
		content.setContent(mm);
		mp2.addBodyPart(content);
		mp2.setSubType("mixed");
		
		message.setContent(mp2);
		message.saveChanges();
		
		return message;
	}
	
}
