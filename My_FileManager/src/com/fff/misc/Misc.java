package com.fff.misc;

import java.util.MissingFormatArgumentException;

import com.fff.FileManager.jj;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Misc {

/*
public static void addShortcut(String name,int iconResid) {
	Activity mActivity = MainActivity.mActivity;
	Context  mContext= MainActivity.mContext;
	
 String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);

    // �������ظ�����
    addShortcutIntent.putExtra("duplicate", false);// �����Բ��Ǹ��ݿ�ݷ�ʽ�������ж��ظ���
    // Ӧ���Ǹ��ݿ�����Intent���ж��Ƿ��ظ���,��Intent.EXTRA_SHORTCUT_INTENT�ֶε�value
    // �������Ʋ�ͬʱ����Ȼ�е��ֻ�ϵͳ����ʾToast��ʾ�ظ�����Ȼ�Ὠ������
    // ��Ļ��û�пռ�ʱ����ʾ
    // ע�⣺�ظ���������ΪMIUI�������ֻ��ϲ�̫һ����С�����ƺ������ظ�������ݷ�ʽ

    // ����
    addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

    // ͼ��
    addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
            Intent.ShortcutIconResource.fromContext(mContext, iconResid));

    // ���ù�������
    Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
    launcherIntent.setClass(mContext, MainActivity.class);
    launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

    addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
    // ���͹㲥
mActivity.sendBroadcast(addShortcutIntent);

}*/
}
