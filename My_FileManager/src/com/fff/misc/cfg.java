package com.fff.misc;

import com.fff.misc.ff;
import android.content.Context;
import android.content.SharedPreferences;
import com.fff.misc.*;
public  class cfg {
public static SharedPreferences read;   
public static	SharedPreferences.Editor write;
	
public static void init(Context c){
	read=c.getSharedPreferences("def", Context.MODE_PRIVATE);
	 write=read.edit();
}
/*public cfg(Context c){
		 pz=c.getSharedPreferences("def", Context.MODE_PRIVATE);
		 put=pz.edit();
		
	}*/
public static String getdef(String key){
	return read.getString(key, null);
};  
public static String getString(String key){
	return read.getString(key, null);
};
public static int getInt(String key){
	return read.getInt(key, 0);
};
public static long getLong(String key){
	return read.getLong(key, 0);
};
public static float getFloat(String key){
	return read.getFloat(key, 0);
};
public static boolean getBoolean(String key){
	return read.getBoolean(key, false);
};


public static <T>void putString(String key,T value){
	  write.putString(key, String.valueOf(value));
	  write.apply();
}

public static void putLong(String key,long value){
	  write.putLong(key, value);
	  write.apply();
}

public static void putInt(String key,int value){
	  write.putInt(key, value);
	  write.apply();
}
public static void putBoolean(String key,Boolean value){
	  write.putBoolean(key, value);
	  write.apply();
}



/*

public static <T>void put(String key,T value){
	
		  if(value instanceof Integer){
			  write.putInt(key, (int)value);
		  }
		  
		  if(value instanceof Boolean){
			write.putBoolean(key, (boolean)value );
		  }
		  
		  
		  if(value instanceof String){
				write.putString(key, (String)value );
			  }
		  
		  
		  if(value instanceof Float){
				write.putFloat(key, (Float)value );
			  }
		  
		  if(value instanceof Long){
				write.putLong(key, (Long)value );
			  }
  
		  write.apply();
	
	}
	*/
          
}
