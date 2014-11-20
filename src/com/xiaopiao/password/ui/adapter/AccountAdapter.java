package com.xiaopiao.password.ui.adapter;

import java.util.List;

import com.xiaopiao.password.R;
import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.model.AccountModel.Field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 账户列表的Adapter
 * 
 * @author qinchaowei
 * 
 */
public class AccountAdapter extends BaseAdapter {

	private Context mContext;
	private List<AccountModel> mAccounts;

	public AccountAdapter(Context context, List<AccountModel> accs) {
		mContext = context;
		mAccounts = accs;
	}

	@Override
	public int getCount() {
		return mAccounts.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.account_list_item, parent, false);
			holder.text1 = (TextView) convertView.findViewById(R.id.text1);
			holder.text2 = (TextView) convertView.findViewById(R.id.text2);
			holder.text3 = (TextView) convertView.findViewById(R.id.text3);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		AccountModel acc = mAccounts.get(position);
		// holder.accountName.setText(acc.account);
		List<Field> fields = acc.fields;
		if (fields.size() > 0) {
			holder.text1.setText(fields.get(0).title + "    "
					+ fields.get(0).content);
		} else {
			holder.text1.setText("");
		}

		if (fields.size() > 1) {
			holder.text2.setText(fields.get(1).title + "    "
					+ fields.get(1).content);
		} else {
			holder.text2.setText("");
		}

		if (fields.size() > 2) {
			holder.text3.setText(fields.get(2).title + "    "
					+ fields.get(2).content);
		} else {
			holder.text3.setText("");
		}

		return convertView;
	}

	/**
	 * 获取指定位置的数据
	 * 
	 * @param position
	 * @return AccountModel or null
	 */
	public AccountModel getData(int position) {
		if (mAccounts != null && position >= 0 && position < mAccounts.size()) {
			return mAccounts.get(position);
		}
		return null;
	}

	static class Holder {
		TextView text1;
		TextView text2;
		TextView text3;
	}
}
