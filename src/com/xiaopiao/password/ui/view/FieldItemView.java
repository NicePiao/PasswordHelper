package com.xiaopiao.password.ui.view;

import java.util.List;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.xiaopiao.password.R;
import com.xiaopiao.password.ui.AddAccountActivity;

/**
 * �˻������ֶοؼ�
 * 
 * @author qinchaowei
 * 
 */
// todo �Ż���һ�ν���ʱ�Ľ���ʵ���
public class FieldItemView extends RelativeLayout implements OnClickListener {

	public static final int ITEM_TITLE = 0;
	public static final int ITEM_CONTENT = 1;

	private AddAccountActivity mAccAddActi;

	private AutoCompleteTextView mFieldTitle;
	private AutoCompleteTextView mInputText;
	private ImageButton mDeleteBtn;

	public FieldItemView(AddAccountActivity acti) {
		super(acti);
		mAccAddActi = acti;
		initView("�ֶ�", true);
	}

	// public FieldItemView(Context context, AttributeSet attrs) {
	// super(context, attrs);
	//
	// TypedArray typeArray = context.obtainStyledAttributes(attrs,
	// R.styleable.FieldItemView);
	// String titleName = typeArray
	// .getString(R.styleable.FieldItemView_filed_title_text);
	// boolean showDelBtn = typeArray.getBoolean(
	// R.styleable.FieldItemView_filed_delete_btn_show, true);
	//
	// initView(titleName, showDelBtn);
	// }

	private void initView(String title, boolean showDelBtn) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.password_field_layout, this);

		mFieldTitle = (AutoCompleteTextView) findViewById(R.id.field_title);
		mInputText = (AutoCompleteTextView) findViewById(R.id.field_input);
		mDeleteBtn = (ImageButton) findViewById(R.id.field_del_btn);

		mDeleteBtn.setOnClickListener(this);
		setTitle(title);
		setShowDelBtn(showDelBtn);
		setAutoCompleteText();
	}

	private void setAutoCompleteText() {
		mFieldTitle.setThreshold(1);
		// mFieldTitle.setCompletionHint(getContext().getString(R.string.history));
		mFieldTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (TextUtils.isEmpty(getFieldTitle())) {
				// showTitleSug(v);
				// }

			}
		});
		mFieldTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus && mAccAddActi.isResume()) {
					showTitleSug(v);
				}

			}
		});

		mInputText.setThreshold(1);
		// mInputText.setCompletionHint(getContext().getString(R.string.history));
		mInputText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (TextUtils.isEmpty(getUserInput())) {
				// showContentSug(v);
				// }
			}
		});

		mInputText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus && mAccAddActi.isResume()) {
					showContentSug(v);
				}
			}
		});
	}

	private void showTitleSug(View view) {
		AutoCompleteTextView autoView = ((AutoCompleteTextView) view);
		if (autoView.isPopupShowing()) {
			return;
		}

		List<String> sugs = mAccAddActi.getSugList(FieldItemView.this,
				ITEM_TITLE);
		if (!sugs.isEmpty()) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getContext(), R.layout.sug_text_item, sugs);
			mFieldTitle.setAdapter(adapter);

			autoView.showDropDown();
		}
	}

	private void showContentSug(View view) {
		AutoCompleteTextView autoView = ((AutoCompleteTextView) view);
		if (autoView.isPopupShowing()) {
			return;
		}

		List<String> sugs = mAccAddActi.getSugList(FieldItemView.this,
				ITEM_CONTENT);
		if (!sugs.isEmpty()) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getContext(), R.layout.sug_text_item, sugs);
			mInputText.setAdapter(adapter);
			autoView.showDropDown();
		}
	}

	/**
	 * ��ȡ�û�����
	 */
	public String getUserInput() {
		if (mInputText.getText() != null) {
			return mInputText.getText().toString();
		} else {
			return "";
		}
	}

	/*
	 * ��ȡ���ֶα���
	 */
	public String getFieldTitle() {
		if (mFieldTitle.getText() != null) {
			return mFieldTitle.getText().toString();
		} else {
			return "";
		}
	}

	/**
	 * �����ֶα���
	 */
	public void setTitle(String titleText) {
		mFieldTitle.setText(titleText);
	}

	/**
	 * �����ı��仯������
	 * 
	 * @param textWatcher
	 */
	public void setTextWatcher(TextWatcher textWatcher) {
		mInputText.addTextChangedListener(textWatcher);
	}

	/**
	 * �����Ƿ���ʾɾ����ť
	 */
	public void setShowDelBtn(boolean showDel) {
		mDeleteBtn.setVisibility(showDel ? View.VISIBLE : View.GONE);
	}

	/**
	 * �ռ��Ƿ�û�б�����
	 * 
	 * @return
	 */
	public boolean isShowing() {
		return getVisibility() == View.VISIBLE;
	}

	/**
	 * ָ���༭����ȡ����
	 */
	public void requestFocusView(int pos) {
		if (pos == ITEM_TITLE) {
			mFieldTitle.requestFocus();
		} else if (pos == ITEM_CONTENT) {
			mInputText.requestFocus();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.field_del_btn:
			setVisibility(View.GONE);// TODO:�ȼ򵥴���
			break;
		default:
			break;
		}

	}

}
