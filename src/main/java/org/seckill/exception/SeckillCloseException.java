package org.seckill.exception;

/**
 * ��ɱ�ر��쳣
 * @author Administrator
 *
 */
public class SeckillCloseException extends SeckillException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeckillCloseException(String message){
		super(message);
	}
	
	public SeckillCloseException(String message,Throwable cause){
		super(message,cause);
	}

}
