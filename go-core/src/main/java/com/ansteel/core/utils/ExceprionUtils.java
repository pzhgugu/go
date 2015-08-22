package com.ansteel.core.utils;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ansteel.core.exception.PageException;

public class ExceprionUtils {
	
	public static void BindingResultError(BindingResult result) {
		StringBuffer sbError = new StringBuffer();
		List<ObjectError> errors = result.getAllErrors();
		for(ObjectError object :errors){
			/*sbError.append("[");
			sbError.append(object.getCodes()[1]);
			sbError.append("],");*/
			sbError.append(object.getDefaultMessage());
			//sbError.append("!\r\n");
			
		}
		throw new PageException(sbError.toString());
	}
}
