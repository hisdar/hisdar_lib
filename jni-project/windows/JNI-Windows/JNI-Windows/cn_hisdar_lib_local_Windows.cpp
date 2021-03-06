/* DO NOT EDIT THIS FILE - it is machine generated */
#include "stdafx.h"
#include "cn_hisdar_lib_local_Windows.h"
/* Header for class cn_hisdar_lib_local_Windows */

/*
 * Class:     cn_hisdar_lib_local_Windows
 * Method:    createShortCut
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
 */

CString JString_To_CString( JNIEnv *m_penv, jstring jnistr )
{
	// We modified in this function because we founf if the length is of two chars
	// it add a rubish char concatenated at the end of the string

	CString dummyCString, retCString;
	jsize   istringlength;

	jboolean isCopy = JNI_TRUE;
	const jchar *pChar = m_penv->GetStringChars(jnistr,&isCopy);

	istringlength = m_penv->GetStringLength(jnistr);
	dummyCString = (BSTR) pChar;
	retCString = dummyCString.Mid(0, istringlength);

	m_penv->ReleaseStringChars(jnistr, pChar);
	return retCString;
}

JNIEXPORT jboolean JNICALL Java_cn_hisdar_lib_local_Windows_createShortCut
	(JNIEnv *jev, jobject jobj, jstring srcPath, jstring tagFolder, jstring param)
{
	HShortCut shortCut;

	CString cSrcPath;
	CString cTagFolder;
	CString cParam;

	cSrcPath = JString_To_CString(jev, srcPath);
	cTagFolder = JString_To_CString(jev, tagFolder);
	cParam = JString_To_CString(jev, param);

	BOOL bRet = shortCut.CreateShortCut(cSrcPath, cTagFolder, cParam);

	if (bRet)
	{
		return TRUE;
	}
	else
	{
		return FALSE;
	}
}

