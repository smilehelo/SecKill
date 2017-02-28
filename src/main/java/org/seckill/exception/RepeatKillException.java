package org.seckill.exception;


/**
 * 重复秒杀异常（运营期异常）
 * @author Administrator
 *
 */
public class RepeatKillException extends SeckillException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepeatKillException(String message){
		super(message);
	}
	
	public RepeatKillException(String message,Throwable cause){
		super(message,cause);
	}	
}
