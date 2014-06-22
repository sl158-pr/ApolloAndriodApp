package com.droid.validators;

import java.util.StringTokenizer;

public class EmailAddressValidator
{
  public static boolean isValidEmailAddress(String emailAddress)
  {
    // a null string is invalid
    if ( emailAddress == null )
      return false;

    // a string without a "@" is an invalid email address
    if ( emailAddress.indexOf("@") < 0 )
      return false;

    // a string without a "."  is an invalid email address
    if ( emailAddress.indexOf(".") < 0 )
      return false;

    if ( lastEmailFieldTwoCharsOrMore(emailAddress) == false )
      return false;
	return true;

    
  }


  /**
   * Returns true if the last email field (i.e., the country code, or something
   * like .com, .biz, .cc, etc.) is two chars or more in length, which it really
   * must be to be legal.
   */
  private static boolean lastEmailFieldTwoCharsOrMore(String emailAddress)
  {
	if (emailAddress == null) return false;
    StringTokenizer st = new StringTokenizer(emailAddress,".");
    String lastToken = null;
    while ( st.hasMoreTokens() )
    {
      lastToken = st.nextToken();
    }

    if ( lastToken.length() >= 2 )
    {
      return true;
    }
    else
    {
      return false;
    }
  }

}