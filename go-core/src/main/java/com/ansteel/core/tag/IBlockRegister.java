package com.ansteel.core.tag;

import javax.servlet.ServletRequest;

public interface IBlockRegister {

	void run(ServletRequest request, String id);

}
