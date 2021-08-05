package com.oracle.example.config.universal;


import com.oracle.example.module.ChannelEntity;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 *
 * 配置常量
 */
@Component
@Data
@PropertySource("classpath:chuanglan-application.properties")
public class ConfigStore {

	@Value("${channel}")
	private String CHANNEL;

	@Value("${variate}")
	private String VARIATE;

	@Value("${mailing}")
	private String MAILING;

	@Value("${send}")
	private String SEND;

	@Value("${weight}")
	private int WEIGHT;

	@Value("${account}")
	private String account;

	@Value("${password}")

	private String password;

	@Value("${formworkmsg}")
	private String formworkmsg;

	//创蓝url前缀
	@Value("${url}")
	private String CHUANGLANURLPRE;

	//创蓝发送短信url
	@Value("${sendsmsurl}")
	private String CHUANGLANSENDSMSURL;

	//创蓝发送变量短信url
	@Value("${variatesendsmsurl}")
	private String CHUANGLANVARIATESENDSMSURL;

	public String VARIATE() {
		return CHANNEL + VARIATE;
	}

	public String SEND() {
		return SEND;
	}

	public int WEIGHT() {return WEIGHT;}

	public String MAILING() {
		return CHANNEL + MAILING;
	}

	public String CHUANGLANURLPRE() {
		return CHUANGLANURLPRE;
	}

	public String CHUANGLANSENDSMSURL() {
		return CHUANGLANSENDSMSURL;
	}

	public ChannelEntity mailingChannelEntity(String channel) {
		return new ChannelEntity(selectChannel(channel) + MAILING);
	}

	public ChannelEntity variateChannelEntity(String channel) {return new ChannelEntity(selectChannel(channel)+VARIATE);}

	private String selectChannel(String channel){
		if(StringUtils.isNotEmpty(channel)){
			return CHANNEL.equals(channel)?CHANNEL:channel;
		}
		return CHANNEL;
	}
}



