// JNI-Windows.h : JNI-Windows DLL ����ͷ�ļ�
//

#pragma once

#ifndef __AFXWIN_H__
	#error "�ڰ������ļ�֮ǰ������stdafx.h�������� PCH �ļ�"
#endif

#include "resource.h"		// ������


// CJNIWindowsApp
// �йش���ʵ�ֵ���Ϣ������� JNI-Windows.cpp
//

class CJNIWindowsApp : public CWinApp
{
public:
	CJNIWindowsApp();

// ��д
public:
	virtual BOOL InitInstance();

	DECLARE_MESSAGE_MAP()
};
