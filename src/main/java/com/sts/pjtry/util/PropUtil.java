package com.sts.pjtry.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropUtil {
	private static final Logger log = LoggerFactory.getLogger( PropUtil.class );
	private static HashMap HM_PROP = new HashMap();

	private static PropUtil instance = null;

	private PropUtil(){
		super();
	}

	public static PropUtil getInstance(){

		if(instance == null) {
			instance = new PropUtil();
		}
		return instance;
	}


	/**
	 * @param propCode
	 * @return
	 */
	public String getPropValue( String propertiFilePath, String propCode ) {
		return  getValues( propertiFilePath , propCode)  ;
	}


    /**
     * @param fileName
     * @param propName
     * @return
     */
    public String getValues (String fileName, String propName){
        String value = null;
		Properties prop = null;

		// HM_PROP 에서 해당 hm 있으면
		 try{
			  prop = (Properties)HM_PROP.get( fileName ) ;
		 }catch(Exception e1 ) {
			 log.error( "[getPropValues] - e1 :" + e1);
		 }

		 if( prop == null ) {
			 try{
				 InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

				 prop = new Properties();
				 prop.load( fis );
				 HM_PROP.put ( fileName , prop ) ;

				 fis.close();
			 }catch( Exception e2 ) {
				 log.error( "[getPropValues] - e2 :" + e2);
			 }
		 }

        try{
            value = new String(prop.getProperty(propName, null ));
            value = Uni2Utf( value );
        }catch(Exception e){
        	log.error(
            "해당 properties["+propName+"]가 존재하지 않습니다.1==>" + fileName + "=="+ e);
        }
        return value;
    }

    public String Uni2Utf(String UnicodeStr) {
  	  try{
  	      if (UnicodeStr == null) {
  	          return null;
  	      } else {
  	          return new String(UnicodeStr.getBytes("ISO-8859-1"), "UTF-8");
  	      }
  	  }catch( Exception e ) {
  		  log.error( e.toString() );
  	  }
  	  return UnicodeStr;
    }
}
