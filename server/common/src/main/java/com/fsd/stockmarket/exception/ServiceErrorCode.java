package com.fsd.stockmarket.exception;

public enum ServiceErrorCode {

	BAD_REQUEST(400),
			
	INTERNAL_ERROR(500);
	
	private int value = 0;  
	  
    private ServiceErrorCode(int value) {
        this.value = value;  
    }  
  
    public static ServiceErrorCode valueOf(int value) {
        switch (value) {  
        case 400:  
            return BAD_REQUEST;  
        case 500:  
            return INTERNAL_ERROR;  
        default:  
            return null;  
        }  
    }  
  
    public int value() {  
        return this.value;  
    }  
}
