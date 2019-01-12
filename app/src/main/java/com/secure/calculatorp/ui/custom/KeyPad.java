package com.secure.calculatorp.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.secure.calculatorp.R;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class KeyPad extends GridLayout {

    private OnKeyUpdateListener keyUpdateListener;
    private String strKey = "";
    private String operatorSelected = "";
    private String charActivate;
    private boolean isPinMode;
    private HashSet<Button> listNumberButtons = new HashSet<>();
    private HashSet<Button> listOperatorButtons = new HashSet<>();

    @BindView(R.id.btn_key_one)
    Button btnOne;
    @BindView(R.id.btn_key_two)
    Button btnTwo;
    @BindView(R.id.btn_key_three)
    Button btnThree;
    @BindView(R.id.btn_key_four)
    Button btnFour;
    @BindView(R.id.btn_key_five)
    Button btnFive;
    @BindView(R.id.btn_key_six)
    Button btnSix;
    @BindView(R.id.btn_key_seven)
    Button btnSeven;
    @BindView(R.id.btn_key_eight)
    Button btnEight;
    @BindView(R.id.btn_key_nine)
    Button btnNine;
    @BindView(R.id.btn_key_zero)
    Button btnZero;
    @BindView(R.id.btn_key_plus)
    Button btnPlus;
    @BindView(R.id.btn_key_backspace)
    Button btnBackSpace;
    @BindView(R.id.btn_key_equal)
    Button btnEqual;
    @BindView(R.id.btn_key_clear)
    Button btnClear;
    @BindView(R.id.btn_key_minus)
    Button btnMinus;
    @BindView(R.id.btn_key_multiply)
    Button btnMultiply;
    @BindView(R.id.btn_key_decimal)
    Button btnDecimal;
    @BindView(R.id.btn_key_division)
    Button btnDivision;
    @BindView(R.id.btn_key_percentage)
    Button btnPercentage;
    private Unbinder unbinder;

    public KeyPad(Context context) {
        super(context);
        init(null, context, 0);
    }

    public KeyPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context, 0);
    }

    public KeyPad(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, context, defStyle);
    }

    private void init(AttributeSet attrs, Context context
            , int defStyle) {

        inflate(context, R.layout.key_pad_view, this);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.KeyPad, defStyle, 0);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
        initKeys();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    private void initKeys() {

        listNumberButtons.add(btnOne);
        listNumberButtons.add(btnTwo);
        listNumberButtons.add(btnThree);
        listNumberButtons.add(btnFour);
        listNumberButtons.add(btnFive);
        listNumberButtons.add(btnSix);
        listNumberButtons.add(btnSeven);
        listNumberButtons.add(btnEight);
        listNumberButtons.add(btnNine);
        listNumberButtons.add(btnZero);
        listNumberButtons.add(btnDecimal);

        listOperatorButtons.add(btnPlus);
        listOperatorButtons.add(btnMinus);
        listOperatorButtons.add(btnDivision);
        listOperatorButtons.add(btnMultiply);

        for (Button btn :
                listNumberButtons) {
            btn.setOnClickListener(numberClickListener);
        }
        for (Button btn :
                listOperatorButtons) {
            btn.setOnClickListener(numberClickListener);
        }

        btnClear.setOnClickListener(numberClickListener);
        btnBackSpace.setOnClickListener(numberClickListener);
        btnEqual.setOnClickListener(numberClickListener);
        btnPercentage.setOnClickListener(numberClickListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private OnClickListener numberClickListener = view -> {
        if (keyUpdateListener != null) {
            String strBtn = ((Button) view).getText().toString();
            keyUpdateListener.onKeyUpdate(strBtn);
        }
    };

    private String getStrKey(View view) {
        String strBtn = ((Button) view).getText().toString();

        if (isPinMode) {
            strKey += strBtn;
        } else {
            if (listNumberButtons.contains(view)) {
                strKey += strBtn;
            } else if (listOperatorButtons.contains(view)) {
                if (operatorSelected.isEmpty()) {
                    strKey += strBtn;
                    operatorSelected = strBtn;
                } else {
                    strKey = strKey.substring(0, strKey.length() - 1) + strBtn;
                    operatorSelected = strBtn;
                }
            } else {
                if (view == btnClear) {
                    strKey = "";
                } else if (view == btnBackSpace) {
                    if (strKey.length() > 0)
                        strKey = strKey.substring(0, strKey.length() - 1);
                } else if (view == btnEqual && !operatorSelected.isEmpty()) {
                    strKey = getCalculation(operatorSelected, strKey);
                    operatorSelected = "";
                } else if (view == btnPercentage) {
                    if (!operatorSelected.isEmpty()) {
                        strKey = getCalculation(operatorSelected, strKey);
                        if (!strKey.isEmpty()) {
                            strKey = getPercentage(strKey);
                        }
                    } else {
                        strKey = getPercentage(strKey);
                    }
                    operatorSelected = "";
                }
            }
        }

        return strKey.isEmpty() ? "0" : strKey;
    }

    private String getPercentage(String number) {
        try {
            return String.valueOf(Float.valueOf(number) / 100);
        } catch (NumberFormatException e) {
            return number;
        }
    }

    private String getCalculation(String operatorSelected, String strKey) {
        String result = "";
        switch (operatorSelected) {
            case "+": {
                String[] tokens = strKey.split("\\+");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) + Float.valueOf(tokens[1]));
                }
                break;
            }
            case "-": {
                String[] tokens = strKey.split("-");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) - Float.valueOf(tokens[1]));
                }
                break;
            }
            case "/": {
                String[] tokens = strKey.split("/");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) / Float.valueOf(tokens[1]));
                }
                break;
            }
            case "x": {
                String[] tokens = strKey.split("x");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) * Float.valueOf(tokens[1]));
                }
                break;
            }
        }
        return result;
    }

    @Override
    public void addOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        super.addOnUnhandledKeyEventListener(listener);
    }

    public void setKeyUpdateListener(OnKeyUpdateListener keyUpdateListener) {
        this.keyUpdateListener = keyUpdateListener;
    }

    public void setCharActivate(String charActivate) {
        this.charActivate = charActivate;
    }

    public boolean isPinMode() {
        return isPinMode;
    }

    public void setPinMode(boolean pinMode) {
        isPinMode = pinMode;
    }

    public interface OnKeyUpdateListener {
        void onKeyUpdate(String key);
    }

}
