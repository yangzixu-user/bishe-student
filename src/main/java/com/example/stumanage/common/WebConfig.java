package com.example.stumanage.common;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 时间显示格式转化配置
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public FastJsonHttpMessageConverter4 jsonHttpMessageConverter() {
		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();

		ParserConfig.getGlobalInstance().setAsmEnable(false);

		FastJsonConfig jsonConfig = new FastJsonConfig();
		jsonConfig.setDateFormat("yyyy-MM-dd");

		//修复BigDecimal如果是0.000000000 显示科学计数法问题
		jsonConfig.setSerializerFeatures(SerializerFeature.WriteBigDecimalAsPlain,SerializerFeature.WriteMapNullValue);

		converter.setFastJsonConfig(jsonConfig);
		return converter;
	}


}
