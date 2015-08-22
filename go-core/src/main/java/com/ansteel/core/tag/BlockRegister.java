package com.ansteel.core.tag;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.exception.PageException;

@Service
public class BlockRegister implements IBlockRegister {
	
	private Map<String ,IBlockService> register;
	
	

	public Map<String, IBlockService> getRegister() {
		return register;
	}



	public void setRegister(Map<String, IBlockService> register) {
		this.register = register;
	}



	@Override
	public void run(ServletRequest request, String id) {
		if(register==null){
			return ;
		}
		if(register.containsKey(id)){
			IBlockService blockService = register.get(id);
			blockService.doStart(request);
		}
	}
	
	

}
