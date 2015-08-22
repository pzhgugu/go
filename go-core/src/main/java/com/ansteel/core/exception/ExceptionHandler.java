package com.ansteel.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ansteel.core.utils.RedirectAttributesEx;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.dhtmlx.xml.ErrorData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：异常拦截器，用异常的统一拦截，区分ajax请求与普通请求。  
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//控制台打印错误
		ex.printStackTrace();
		if (ex instanceof PageException||ex instanceof IllegalArgumentException) {
			if (!"XMLHttpRequest".equalsIgnoreCase(request
					.getHeader("X-Requested-With"))) {// 不是ajax请求
				/*try {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new ModelAndView();*/
				String message = ex.getMessage();
				RedirectAttributesEx rae=new RedirectAttributesEx(request);
				rae.setAttribute("ERROR_MESSAGE", message);
				ModelAndView mav=new ModelAndView();
				mav.setViewName("redirect:/error");
				return mav;

			} else {

				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				String result = null;
				String type = "";
				if (request.getParameterMap().containsKey("format")) {
					type = request.getParameter("format");
				}
				if (type.equals("xml")) {
					ErrorData error =new ErrorData();
					ErrorMessage message = new ErrorMessage();
					message.setCode(-1);
					message.setMessage(ex.getMessage());
					error.getAction().add(message);
					try {
						JAXBContext context = JAXBContext
								.newInstance(ErrorData.class);
						Marshaller marshaller = context.createMarshaller();
						marshaller.setProperty(
								Marshaller.JAXB_FORMATTED_OUTPUT, true);
						marshaller.setProperty(Marshaller.JAXB_ENCODING,
								"UTF-8");

						StringWriter writer = new StringWriter();
						marshaller.marshal(error, writer);
						result = writer.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					ErrorMessage message = new ErrorMessage();
					message.setCode(-1);
					message.setMessage(ex.getMessage());
					ObjectMapper objectMapper = new ObjectMapper();

					try {
						result = objectMapper.writeValueAsString(message);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				try {
					PrintWriter writer = response.getWriter();
					writer.write(result);
					writer.flush();
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return null;

	}

}
