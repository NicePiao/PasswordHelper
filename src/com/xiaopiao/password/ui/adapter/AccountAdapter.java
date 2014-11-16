package com.xiaopiao.password.ui.adapter;

import java.util.List;

import com.xiaopiao.password.R;
import com.xiaopiao.password.model.AccountModel;

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
			holder.accountName = (TextView) convertView
					.findViewById(R.id.account_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		AccountModel acc = mAccounts.get(position);
		holder.accountName.setText(acc.account);
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
		TextView accountName;
	}
}
