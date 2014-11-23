package com.xiaopiao.password.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaopiao.password.R;
import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.model.AccountModel.Field;
import com.xiaopiao.password.storage.database.helper.AccountHelper;

/**
 * ’À∫≈œÍ«È’π æDialog
 * 
 * @author qinchaowei
 * 
 */
public class ContentDialog extends Dialog {

	private AccountModel mData;

	public ContentDialog(Context context, String uuid) {
		super(context);
		mData = AccountHelper.getInstance(context).getAccount(uuid);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.psd_detail_layout);
		initView();
	}

	private void initView() {
		TextView contentView = (TextView) findViewById(R.id.content);
		StringBuilder sb = new StringBuilder();
		if (mData != null) {
			sb.append(mData.account).append("\r\n");
			for (Field field : mData.fields) {
				sb.append(field.title).append("    ").append(field.content)
						.append("\r\n");
			}
		} else {
			sb.append(getContext().getString(R.string.no_content));
		}
		contentView.setText(sb.toString());
	}
}
