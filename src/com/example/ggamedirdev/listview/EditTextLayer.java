package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.GetChars;
import android.text.InputFilter;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DateKeyListener;
import android.text.method.DateTimeKeyListener;
import android.text.method.DialerKeyListener;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TextKeyListener;
import android.text.method.TimeKeyListener;
import android.text.method.TransformationMethod;
import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;
import android.text.style.SuggestionSpan;
import android.text.style.UpdateAppearance;
import android.text.util.Linkify;
import android.view.GestureDetector;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView.BufferType;

import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.stage.StageManager;

public class EditTextLayer extends Layer{
	private List<? extends ILayer> mlayers;
	private float itemHeight;
	private boolean hasButtonColors;
	private int[] buttonColors = new int[3];
	private Bitmap[] buttonBitmaps = new Bitmap[3];
	
	private final int NORMAL_INDEX = 0;
	private final int DOWN_INDEX = 1;
	private final int UP_INDEX = 2;
	
	boolean isClickCancled = false;
	
	GestureDetector gestureDetector;
	
	List<ILayer> tabs;
    
	boolean autotext = false;
	TextKeyListener keyListener = new TextKeyListener(TextKeyListener.Capitalize.NONE, autotext);
	
	View targetView;
	
	private boolean mSingleLine;

    // display attributes
    private final TextPaint mTextPaint = new TextPaint();
    
    private TransformationMethod mTransformation;
    private CharSequence mText;
    private CharSequence mTransformed;
    
    Context mContext = StageManager.getCurrentStage();
    
    // Enum for the "typeface" XML parameter.
    // TODO: How can we get this from the XML instead of hardcoding it here?
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int MONOSPACE = 3;
    
    private boolean mAllowTransformationLengthChange;
    private boolean mUserSetTextScaleX;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private InputFilter[] mFilters = NO_FILTERS;
    private static final Spanned EMPTY_SPANNED = new SpannedString("");
    
    protected int mScrollX;
	protected int mScrollY;
	
	private int mCurTextColor;
	private final Paint mHighlightPaint = new Paint();
	private Path mHighlightPath;
	int mHighlightColor = 0x6633B5E5;
	private boolean mHighlightPathBogus = true;
	
	private LabelLayer mLayout;
	
	private ArrayList<TextWatcher> mListeners;
	
	private boolean mHorizontallyScrolling;
	
    private static final int LINES = 1;
    private int mMaximum = Integer.MAX_VALUE;
    private int mMaxMode = LINES;
    private int mMinimum = 0;
    private int mMinMode = LINES;
    private BufferType mBufferType = BufferType.NORMAL;
    private CharWrapper mCharWrapper;
    private Editable.Factory mEditableFactory = Editable.Factory.getInstance();
    private Spannable.Factory mSpannableFactory = Spannable.Factory.getInstance();
    private int mAutoLinkMask;
    private boolean mLinksClickable = true;
    private ChangeWatcher mChangeWatcher;
    private static final int CHANGE_WATCHER_PRIORITY = 100;
    
    private CharSequence mHint;
    private LabelLayer mHintLayout;
    private ColorStateList mHintTextColor;
    private int mCurHintTextColor;
    protected int mTop;
    protected int mBottom;
    
    private void requestLayout(){
    	final int count = getChildCount();
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        final int scrollX = getScrollX();
    }
    
    private int getScrollX() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getPaddingBottom() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getPaddingRight() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getPaddingTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getPaddingLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
     * Returns whether the text is allowed to be wider than the View is.
     * If false, the text will be wrapped to the width of the View.
     *
     * @attr ref android.R.styleable#TextView_scrollHorizontally
     * @hide
     */
    public boolean getHorizontallyScrolling() {
        return mHorizontallyScrolling;
    }
    
    
    public boolean isTextSelectable() {
        return false;
    }
    
    /**
     * @return True iff this TextView contains a text that can be edited, or if this is
     * a selectable TextView.
     */
    boolean isTextEditable() {
        return false;
    }
    
    /**
     * Test based on the <i>intrinsic</i> charateristics of the TextView.
     * The text must be spannable and the movement method must allow for arbitary selection.
     *
     * See also {@link #canSelectText()}.
     */
    boolean textCanBeSelected() {
        // prepareCursorController() relies on this method.
        // If you change this condition, make sure prepareCursorController is called anywhere
        // the value of this condition might be changed.
        return false;
    }
    
    /**
     * Sets the autolink mask of the text.  See {@link
     * android.text.util.Linkify#ALL Linkify.ALL} and peers for
     * possible values.
     *
     * @attr ref android.R.styleable#TextView_autoLink
     */
    public final void setAutoLinkMask(int mask) {
        mAutoLinkMask = mask;
    }
    
    /**
     * Sets the list of input filters that will be used if the buffer is
     * Editable. Has no effect otherwise.
     *
     * @attr ref android.R.styleable#TextView_maxLength
     */
    public void setFilters(InputFilter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException();
        }

        mFilters = filters;

        if (mText instanceof Editable) {
            setFilters((Editable) mText, filters);
        }
    }
    
    /**
     * Sets the list of input filters on the specified Editable,
     * and includes mInput in the list if it is an InputFilter.
     */
    private void setFilters(Editable e, InputFilter[] filters) {
        e.setFilters(filters);
    }
    
    private static boolean isMultilineInputType(int type) {
        return (type & (EditorInfo.TYPE_MASK_CLASS | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE)) ==
            (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
    }
    
	boolean isSingleLine() {
        return mSingleLine;
    }
	
    public void setSingleLine(boolean singleLine) {
        // Could be used, but may break backward compatibility.
        // if (mSingleLine == singleLine) return;
        setInputTypeSingleLine(singleLine);
        applySingleLine(singleLine, true, true);
    }

    /**
     * Adds or remove the EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE on the mInputType.
     * @param singleLine
     */
    private void setInputTypeSingleLine(boolean singleLine) {
        
    }
    
    private void applySingleLine(boolean singleLine, boolean applyTransformation,
            boolean changeMaxLines) {
        mSingleLine = singleLine;
        if (singleLine) {
            setLines(1);
            setHorizontallyScrolling(true);
            if (applyTransformation) {
                setTransformationMethod(SingleLineTransformationMethod.getInstance());
            }
        } else {
            if (changeMaxLines) {
                setMaxLines(Integer.MAX_VALUE);
            }
            setHorizontallyScrolling(false);
            if (applyTransformation) {
                setTransformationMethod(null);
            }
        }
    }
    
    public void setLines(int lines) {
        mMaximum = mMinimum = lines;
        mMaxMode = mMinMode = LINES;

        requestLayout();
//        invalidate();
    }
    
    public void setMaxLines(int maxlines) {
        mMaximum = maxlines;
        mMaxMode = LINES;

        requestLayout();
//        invalidate();
    }
    
    /**
     * Sets whether the text should be allowed to be wider than the
     * View is.  If false, it will be wrapped to the width of the View.
     *
     * @attr ref android.R.styleable#TextView_scrollHorizontally
     */
    public void setHorizontallyScrolling(boolean whether) {
        if (mHorizontallyScrolling != whether) {
            mHorizontallyScrolling = whether;

            if (mLayout != null) {
//                nullLayouts();
//                requestLayout();
//                invalidate();
            }
        }
    }
	
	public EditTextLayer(View view) {
		// TODO Auto-generated constructor stub
		targetView = view;
		setPosition(70, 70);
		setBackgroundColor(Color.BLUE);
		
		itemHeight = 100;
		
//		initButtons();
//		initSprites();
//		initClipSprites();
		
		String text;
		
		Bitmap mouse;
		
		Bitmap frame;
		
		
		final Layer layer = new Layer();
		
		ButtonLayer buttonLayer = new ButtonLayer("XX", 100, 50, false);
		buttonLayer.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				layer.setBackgroundColor(Color.MAGENTA);
			}
		});
	}
	
    /**
     * Get the type of the editable content.
     *
     * @see #setInputType(int)
     * @see android.text.InputType
     */
    public int getInputType() {
        return EditorInfo.TYPE_NULL;
    }

    
    /**
     * Set the type of the content with a constant as defined for {@link EditorInfo#inputType}. This
     * will take care of changing the key listener, by calling {@link #setKeyListener(KeyListener)},
     * to match the given content type.  If the given content type is {@link EditorInfo#TYPE_NULL}
     * then a soft keyboard will not be displayed for this text view.
     *
     * Note that the maximum number of displayed lines (see {@link #setMaxLines(int)}) will be
     * modified if you change the {@link EditorInfo#TYPE_TEXT_FLAG_MULTI_LINE} flag of the input
     * type.
     *
     * @see #getInputType()
     * @see #setRawInputType(int)
     * @see android.text.InputType
     * @attr ref android.R.styleable#TextView_inputType
     */
    public void setInputType(int type) {
        final boolean wasPassword = isPasswordInputType(getInputType());
        final boolean wasVisiblePassword = isVisiblePasswordInputType(getInputType());
        setInputType(type, false);
        final boolean isPassword = isPasswordInputType(type);
        final boolean isVisiblePassword = isVisiblePasswordInputType(type);
        boolean forceUpdate = false;
        if (isPassword) {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
            setTypefaceFromAttrs(null /* fontFamily */, MONOSPACE, 0);
        } else if (isVisiblePassword) {
            if (mTransformation == PasswordTransformationMethod.getInstance()) {
                forceUpdate = true;
            }
            setTypefaceFromAttrs(null /* fontFamily */, MONOSPACE, 0);
        } else if (wasPassword || wasVisiblePassword) {
            // not in password mode, clean up typeface and transformation
            setTypefaceFromAttrs(null /* fontFamily */, -1, -1);
            if (mTransformation == PasswordTransformationMethod.getInstance()) {
                forceUpdate = true;
            }
        }

        boolean singleLine = !isMultilineInputType(type);

        // We need to update the single line mode if it has changed or we
        // were previously in password mode.
        if (mSingleLine != singleLine || forceUpdate) {
            // Change single line mode, but only change the transformation if
            // we are not in password mode.
            applySingleLine(singleLine, !isPassword, true);
        }

        if (!isSuggestionsEnabled()) {
            mText = removeSuggestionSpans(mText);
        }

//        InputMethodManager imm = InputMethodManager.peekInstance();
        InputMethodManager imm = (InputMethodManager) StageManager.getCurrentStage().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.restartInput(targetView);
    }
    
    private void setTypefaceFromAttrs(String familyName, int typefaceIndex, int styleIndex) {
        Typeface tf = null;
        if (familyName != null) {
            tf = Typeface.create(familyName, styleIndex);
            if (tf != null) {
                setTypeface(tf);
                return;
            }
        }
        switch (typefaceIndex) {
            case SANS:
                tf = Typeface.SANS_SERIF;
                break;

            case SERIF:
                tf = Typeface.SERIF;
                break;

            case MONOSPACE:
                tf = Typeface.MONOSPACE;
                break;
        }

        setTypeface(tf, styleIndex);
    }
    
    public boolean isSuggestionsEnabled() {
        return false;
    }
    
    /**
     * Removes the suggestion spans.
     */
    CharSequence removeSuggestionSpans(CharSequence text) {
       if (text instanceof Spanned) {
           Spannable spannable;
           if (text instanceof Spannable) {
               spannable = (Spannable) text;
           } else {
               spannable = new SpannableString(text);
               text = spannable;
           }

           SuggestionSpan[] spans = spannable.getSpans(0, text.length(), SuggestionSpan.class);
           for (int i = 0; i < spans.length; i++) {
               spannable.removeSpan(spans[i]);
           }
       }
       return text;
    }
	
    private void setInputType(int type, boolean direct) {
        final int cls = type & EditorInfo.TYPE_MASK_CLASS;
        KeyListener input;
        if (cls == EditorInfo.TYPE_CLASS_TEXT) {
            boolean autotext = (type & EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT) != 0;
            TextKeyListener.Capitalize cap;
            if ((type & EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS) != 0) {
                cap = TextKeyListener.Capitalize.CHARACTERS;
            } else if ((type & EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS) != 0) {
                cap = TextKeyListener.Capitalize.WORDS;
            } else if ((type & EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES) != 0) {
                cap = TextKeyListener.Capitalize.SENTENCES;
            } else {
                cap = TextKeyListener.Capitalize.NONE;
            }
            input = TextKeyListener.getInstance(autotext, cap);
        } else if (cls == EditorInfo.TYPE_CLASS_NUMBER) {
            input = DigitsKeyListener.getInstance(
                    (type & EditorInfo.TYPE_NUMBER_FLAG_SIGNED) != 0,
                    (type & EditorInfo.TYPE_NUMBER_FLAG_DECIMAL) != 0);
        } else if (cls == EditorInfo.TYPE_CLASS_DATETIME) {
            switch (type & EditorInfo.TYPE_MASK_VARIATION) {
                case EditorInfo.TYPE_DATETIME_VARIATION_DATE:
                    input = DateKeyListener.getInstance();
                    break;
                case EditorInfo.TYPE_DATETIME_VARIATION_TIME:
                    input = TimeKeyListener.getInstance();
                    break;
                default:
                    input = DateTimeKeyListener.getInstance();
                    break;
            }
        } else if (cls == EditorInfo.TYPE_CLASS_PHONE) {
            input = DialerKeyListener.getInstance();
        } else {
            input = TextKeyListener.getInstance();
        }
        setRawInputType(type);
        if (direct) {
//            mEditor.mKeyListener = input;
        } else {
            setKeyListenerOnly(input);
        }
    }
    
    /**
     * Directly change the content type integer of the text view, without
     * modifying any other state.
     * @see #setInputType(int)
     * @see android.text.InputType
     * @attr ref android.R.styleable#TextView_inputType
     */
    public void setRawInputType(int type) {

    }
    
    private void setKeyListenerOnly(KeyListener input) {

    }
    
    private static boolean isPasswordInputType(int inputType) {
        final int variation =
                inputType & (EditorInfo.TYPE_MASK_CLASS | EditorInfo.TYPE_MASK_VARIATION);
        return variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);
    }
    
    private static boolean isVisiblePasswordInputType(int inputType) {
        final int variation =
                inputType & (EditorInfo.TYPE_MASK_CLASS | EditorInfo.TYPE_MASK_VARIATION);
        return variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }
    
    /**
     * Sets the transformation that is applied to the text that this
     * TextView is displaying.
     *
     * @attr ref android.R.styleable#TextView_password
     * @attr ref android.R.styleable#TextView_singleLine
     */
    public final void setTransformationMethod(TransformationMethod method) {
        if (method == mTransformation) {
            // Avoid the setText() below if the transformation is
            // the same.
            return;
        }
        if (mTransformation != null) {
            if (mText instanceof Spannable) {
                ((Spannable) mText).removeSpan(mTransformation);
            }
        }

        mTransformation = method;

        if (method instanceof TransformationMethod2) {
            TransformationMethod2 method2 = (TransformationMethod2) method;
            mAllowTransformationLengthChange = !isTextSelectable() && !(mText instanceof Editable);
            method2.setLengthChangesAllowed(mAllowTransformationLengthChange);
        } else {
            mAllowTransformationLengthChange = false;
        }

        setText(mText);
    }
    
    public CharSequence getText() {
        return mText;
    }
    
    /**
    * Sets the string value of the TextView. TextView <em>does not</em> accept
    * HTML-like formatting, which you can do with text strings in XML resource files.
    * To style your strings, attach android.text.style.* objects to a
    * {@link android.text.SpannableString SpannableString}, or see the
    * <a href="{@docRoot}guide/topics/resources/available-resources.html#stringresources">
    * Available Resource Types</a> documentation for an example of setting
    * formatted text in the XML resource file.
    *
    * @attr ref android.R.styleable#TextView_text
    */
   public final void setText(CharSequence text) {
       setText(text, mBufferType);
   }
   
   /**
    * Sets the text that this TextView is to display (see
    * {@link #setText(CharSequence)}) and also sets whether it is stored
    * in a styleable/spannable buffer and whether it is editable.
    *
    * @attr ref android.R.styleable#TextView_text
    * @attr ref android.R.styleable#TextView_bufferType
    */
   public void setText(CharSequence text, BufferType type) {
       setText(text, type, true, 0);

       if (mCharWrapper != null) {
           mCharWrapper.mChars = null;
       }
   }

   private void setText(CharSequence text, BufferType type,
                        boolean notifyBefore, int oldlen) {
       if (text == null) {
           text = "";
       }

       // If suggestions are not enabled, remove the suggestion spans from the text
       if (!isSuggestionsEnabled()) {
           text = removeSuggestionSpans(text);
       }

       if (!mUserSetTextScaleX) mTextPaint.setTextScaleX(1.0f);

       int n = mFilters.length;
       for (int i = 0; i < n; i++) {
           CharSequence out = mFilters[i].filter(text, 0, text.length(), EMPTY_SPANNED, 0, 0);
           if (out != null) {
               text = out;
           }
       }

       if (notifyBefore) {
           if (mText != null) {
               oldlen = mText.length();
               sendBeforeTextChanged(mText, 0, oldlen, text.length());
           } else {
               sendBeforeTextChanged("", 0, 0, text.length());
           }
       }

       boolean needEditableForNotification = false;

       if (mListeners != null && mListeners.size() != 0) {
           needEditableForNotification = true;
       }

       if (type == BufferType.EDITABLE || getKeyListener() != null ||
               needEditableForNotification) {
           Editable t = mEditableFactory.newEditable(text);
           text = t;
           setFilters(t, mFilters);
//           InputMethodManager imm = InputMethodManager.peekInstance();
           InputMethodManager imm = (InputMethodManager) StageManager.getCurrentStage().getSystemService(Context.INPUT_METHOD_SERVICE);
           if (imm != null) imm.restartInput(targetView);
       } else if (type == BufferType.SPANNABLE) {
           text = mSpannableFactory.newSpannable(text);
       } else if (!(text instanceof CharWrapper)) {
           text = TextUtils.stringOrSpannedString(text);
       }

       if (mAutoLinkMask != 0) {
           Spannable s2;

           if (type == BufferType.EDITABLE || text instanceof Spannable) {
               s2 = (Spannable) text;
           } else {
               s2 = mSpannableFactory.newSpannable(text);
           }

           if (Linkify.addLinks(s2, mAutoLinkMask)) {
               text = s2;
               type = (type == BufferType.EDITABLE) ? BufferType.EDITABLE : BufferType.SPANNABLE;

               /*
                * We must go ahead and set the text before changing the
                * movement method, because setMovementMethod() may call
                * setText() again to try to upgrade the buffer type.
                */
               mText = text;
           }
       }

       mBufferType = type;
       mText = text;

       if (mTransformation == null) {
           mTransformed = text;
       } else {
           mTransformed = mTransformation.getTransformation(text, targetView);
       }

       final int textLength = text.length();

       if (text instanceof Spannable && !mAllowTransformationLengthChange) {
           Spannable sp = (Spannable) text;

           // Remove any ChangeWatchers that might have come from other TextViews.
           final ChangeWatcher[] watchers = sp.getSpans(0, sp.length(), ChangeWatcher.class);
           final int count = watchers.length;
           for (int i = 0; i < count; i++) {
               sp.removeSpan(watchers[i]);
           }

           if (mChangeWatcher == null) mChangeWatcher = new ChangeWatcher();

           sp.setSpan(mChangeWatcher, 0, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE |
                      (CHANGE_WATCHER_PRIORITY << Spanned.SPAN_PRIORITY_SHIFT));


           if (mTransformation != null) {
               sp.setSpan(mTransformation, 0, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
           }

       }

       sendOnTextChanged(text, 0, oldlen, textLength);
       onTextChanged(text, 0, oldlen, textLength);

       if (needEditableForNotification) {
           sendAfterTextChanged((Editable) text);
       }
   }
   
   /**
    * Not private so it can be called from an inner class without going
    * through a thunk.
    */
   void sendOnTextChanged(CharSequence text, int start, int before, int after) {
       if (mListeners != null) {
           final ArrayList<TextWatcher> list = mListeners;
           final int count = list.size();
           for (int i = 0; i < count; i++) {
               list.get(i).onTextChanged(text, start, before, after);
           }
       }
   }

   /**
    * Not private so it can be called from an inner class without going
    * through a thunk.
    */
   void sendAfterTextChanged(Editable text) {
       if (mListeners != null) {
           final ArrayList<TextWatcher> list = mListeners;
           final int count = list.size();
           for (int i = 0; i < count; i++) {
               list.get(i).afterTextChanged(text);
           }
       }
   }
   
   public final KeyListener getKeyListener() {
       return null;
   }
   
   private void sendBeforeTextChanged(CharSequence text, int start, int before, int after) {
       if (mListeners != null) {
           final ArrayList<TextWatcher> list = mListeners;
           final int count = list.size();
           for (int i = 0; i < count; i++) {
               list.get(i).beforeTextChanged(text, start, before, after);
           }
       }

       // The spans that are inside or intersect the modified region no longer make sense
       removeIntersectingNonAdjacentSpans(start, start + before, SuggestionSpan.class);
   }

   // Removes all spans that are inside or actually overlap the start..end range
   private <T> void removeIntersectingNonAdjacentSpans(int start, int end, Class<T> type) {
       if (!(mText instanceof Editable)) return;
       Editable text = (Editable) mText;

       T[] spans = text.getSpans(start, end, type);
       final int length = spans.length;
       for (int i = 0; i < length; i++) {
           final int spanStart = text.getSpanStart(spans[i]);
           final int spanEnd = text.getSpanEnd(spans[i]);
           if (spanEnd == start || spanStart == end) break;
           text.removeSpan(spans[i]);
       }
   }

   private class ChangeWatcher implements TextWatcher, SpanWatcher {

       private CharSequence mBeforeText;

       public void beforeTextChanged(CharSequence buffer, int start,
                                     int before, int after) {

           if (((!isPasswordInputType(getInputType()) && !hasPasswordTransformationMethod()))) {
               mBeforeText = buffer.toString();
           }

           EditTextLayer.this.sendBeforeTextChanged(buffer, start, before, after);
       }

       public void onTextChanged(CharSequence buffer, int start, int before, int after) {
    	   EditTextLayer.this.handleTextChanged(buffer, start, before, after);

       }

       public void afterTextChanged(Editable buffer) {
    	   EditTextLayer.this.sendAfterTextChanged(buffer);

//           if (MetaKeyKeyListener.getMetaState(buffer, MetaKeyKeyListener.META_SELECTING) != 0) {
//               MetaKeyKeyListener.stopSelecting(targetView, buffer);
//           }
       }

       public void onSpanChanged(Spannable buf, Object what, int s, int e, int st, int en) {
    	   EditTextLayer.this.spanChange(buf, what, s, st, e, en);
       }

       public void onSpanAdded(Spannable buf, Object what, int s, int e) {
    	   EditTextLayer.this.spanChange(buf, what, -1, s, -1, e);
       }

       public void onSpanRemoved(Spannable buf, Object what, int s, int e) {
    	   EditTextLayer.this.spanChange(buf, what, s, -1, e, -1);
       }
   }
   
   /**
    * Not private so it can be called from an inner class without going
    * through a thunk.
    */
   void spanChange(Spanned buf, Object what, int oldStart, int newStart, int oldEnd, int newEnd) {
       // XXX Make the start and end move together if this ends up
       // spending too much time invalidating.

       boolean selChanged = false;
       int newSelStart=-1, newSelEnd=-1;

       if (what == Selection.SELECTION_END) {
           selChanged = true;
           newSelEnd = newStart;

           if (oldStart >= 0 || newStart >= 0) {
               invalidateCursor(Selection.getSelectionStart(buf), oldStart, newStart);
               checkForResize();
//               registerForPreDraw();
           }
       }

       if (what == Selection.SELECTION_START) {
           selChanged = true;
           newSelStart = newStart;

           if (oldStart >= 0 || newStart >= 0) {
               int end = Selection.getSelectionEnd(buf);
               invalidateCursor(end, oldStart, newStart);
           }
       }

       if (selChanged) {
           mHighlightPathBogus = true;

           if ((buf.getSpanFlags(what)&Spanned.SPAN_INTERMEDIATE) == 0) {
               if (newSelStart < 0) {
                   newSelStart = Selection.getSelectionStart(buf);
               }
               if (newSelEnd < 0) {
                   newSelEnd = Selection.getSelectionEnd(buf);
               }
               onSelectionChanged(newSelStart, newSelEnd);
           }
       }

       if (what instanceof UpdateAppearance || what instanceof ParagraphStyle ||
               what instanceof CharacterStyle) {

//               invalidate();
//               mHighlightPathBogus = true;
//               checkForResize();
       }
   }
   
   
   /**
    * Check whether a change to the existing text layout requires a
    * new view layout.
    */
   private void checkForResize() {
       boolean sizeChanged = false;

//       if (mLayout != null) {
//           // Check if our width changed
//           if (mLayoutParams.width == LayoutParams.WRAP_CONTENT) {
//               sizeChanged = true;
//               invalidate();
//           }
//
//           // Check if our height changed
//           if (mLayoutParams.height == LayoutParams.WRAP_CONTENT) {
//               int desiredHeight = getDesiredHeight();
//
//               if (desiredHeight != this.getHeight()) {
//                   sizeChanged = true;
//               }
//           } else if (mLayoutParams.height == LayoutParams.MATCH_PARENT) {
//               if (mDesiredHeightAtMeasure >= 0) {
//                   int desiredHeight = getDesiredHeight();
//
//                   if (desiredHeight != mDesiredHeightAtMeasure) {
//                       sizeChanged = true;
//                   }
//               }
//           }
//       }

       if (sizeChanged) {
           requestLayout();
           // caller will have already invalidated
       }
   }
   
   void invalidateCursor() {
       int where = getSelectionEnd();

       invalidateCursor(where, where, where);
   }
   
   /**
    * Convenience for {@link Selection#getSelectionEnd}.
    */
   public int getSelectionEnd() {
       return Selection.getSelectionEnd(getText());
   }

   private void invalidateCursor(int a, int b, int c) {
       if (a >= 0 || b >= 0 || c >= 0) {
           int start = Math.min(Math.min(a, b), c);
           int end = Math.max(Math.max(a, b), c);
           invalidateRegion(start, end, true /* Also invalidates blinking cursor */);
       }
   }

   int getLineTop(int lineStart){
	   return 1;
   }
   
   /**
    * Return the vertical position of the bottom of the specified line.
    */
   public final int getLineBottom(int line) {
       return getLineTop(line + 1);
   }
   
   /**
    * Invalidates the region of text enclosed between the start and end text offsets.
    */
   void invalidateRegion(int start, int end, boolean invalidateCursor) {
               int lineStart = getLineForOffset(start);
               int top = getLineTop(lineStart);

               int lineEnd;

               if (start == end)
                   lineEnd = lineStart;
               else
                   lineEnd = getLineForOffset(end);

               int bottom = getLineBottom(lineEnd);


               final int compoundPaddingLeft = getCompoundPaddingLeft();
               final int verticalPadding = getExtendedPaddingTop() + getVerticalOffset(true);

               int left, right;
               if (lineStart == lineEnd && !invalidateCursor) {
                   left = (int) getLeft();
                   right = (int) (getLeft() + getWidth());
                   left += compoundPaddingLeft;
                   right += compoundPaddingLeft;
               } else {
                   // Rectangle bounding box when the region spans several lines
                   left = compoundPaddingLeft;
                   right = getWidth() - getCompoundPaddingRight();
               }

               invalidate(mScrollX + left, verticalPadding + top,
                       mScrollX + right, verticalPadding + bottom);
   }
   
   /**
    * Returns the extended top padding of the view, including both the
    * top Drawable if any and any extra space to keep more than maxLines
    * of text from showing.  It is only valid to call this after measuring.
    */
   public int getExtendedPaddingTop() {
       if (mMaxMode != LINES) {
           return getCompoundPaddingTop();
       }

       if (getLineCount() <= mMaximum) {
           return getCompoundPaddingTop();
       }

       int top = getCompoundPaddingTop();
       int bottom = getCompoundPaddingBottom();
       int viewht = getHeight() - top - bottom;
       int layoutht = getLineTop(mMaximum);

       if (layoutht >= viewht) {
           return top;
       }
       
       return top;
   }
   
   public int getCompoundPaddingLeft() {
	   return 0;
   }
   
   public int getCompoundPaddingRight() {
	   return 0;
   }
   public int getCompoundPaddingTop() {
	   return 0;
   }
   
   public int getCompoundPaddingBottom() {
	   return 0;
   }
   
   /**
    * It would be better to rely on the input type for everything. A password inputType should have
    * a password transformation. We should hence use isPasswordInputType instead of this method.
    *
    * We should:
    * - Call setInputType in setKeyListener instead of changing the input type directly (which
    * would install the correct transformation).
    * - Refuse the installation of a non-password transformation in setTransformation if the input
    * type is password.
    *
    * However, this is like this for legacy reasons and we cannot break existing apps. This method
    * is useful since it matches what the user can see (obfuscated text or not).
    *
    * @return true if the current transformation method is of the password type.
    */
   private boolean hasPasswordTransformationMethod() {
       return mTransformation instanceof PasswordTransformationMethod;
   }

    
   /**
    * Sets the typeface and style in which the text should be displayed,
    * and turns on the fake bold and italic bits in the Paint if the
    * Typeface that you provided does not have all the bits in the
    * style that you specified.
    *
    * @attr ref android.R.styleable#TextView_typeface
    * @attr ref android.R.styleable#TextView_textStyle
    */
   public void setTypeface(Typeface tf, int style) {
       if (style > 0) {
           if (tf == null) {
               tf = Typeface.defaultFromStyle(style);
           } else {
               tf = Typeface.create(tf, style);
           }

           setTypeface(tf);
           // now compute what (if any) algorithmic styling is needed
           int typefaceStyle = tf != null ? tf.getStyle() : 0;
           int need = style & ~typefaceStyle;
           mTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
           mTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
       } else {
           mTextPaint.setFakeBoldText(false);
           mTextPaint.setTextSkewX(0);
           setTypeface(tf);
       }
   }
   
   /**
    * Sets the typeface and style in which the text should be displayed.
    * Note that not all Typeface families actually have bold and italic
    * variants, so you may need to use
    * {@link #setTypeface(Typeface, int)} to get the appearance
    * that you actually want.
    *
    * @see #getTypeface()
    *
    * @attr ref android.R.styleable#TextView_fontFamily
    * @attr ref android.R.styleable#TextView_typeface
    * @attr ref android.R.styleable#TextView_textStyle
    */
   public void setTypeface(Typeface tf) {
       if (mTextPaint.getTypeface() != tf) {
           mTextPaint.setTypeface(tf);

//           if (mLayout != null) {
//               nullLayouts();
//               requestLayout();
//               invalidate();
//           }
       }
   }
   
   
   @Override
   public void drawSelf(Canvas canvas, Paint paint) {
       // Draw the background for this view
       super.drawSelf(canvas, paint);

       boolean isLayoutRtl = true;
       final int compoundPaddingLeft = getCompoundPaddingLeft();
       final int compoundPaddingTop = getCompoundPaddingTop();
       final int compoundPaddingRight = getCompoundPaddingRight();
       final int compoundPaddingBottom = getCompoundPaddingBottom();
       final int scrollX = mScrollX;
       final int scrollY = mScrollY;
       final int right = (int) (getLeft() + getWidth());
       final int left = (int) getLeft();
       final int bottom = (int) (getTop() + getHeight());
       final int top = (int) getTop();
       final int offset = 0;
       final int leftOffset = isLayoutRtl ? 0 : offset;
       final int rightOffset = isLayoutRtl ? offset : 0 ;

       int color = mCurTextColor;

       LabelLayer layout = mLayout;

       if (mHint != null && mText.length() == 0) {
           if (mHintTextColor != null) {
               color = mCurHintTextColor;
           }

           layout = mHintLayout;
       }

       mTextPaint.setColor(color);

       canvas.save();
       /*  Would be faster if we didn't have to do this. Can we chop the
           (displayable) text so that we don't need to do this ever?
       */

       int extendedPaddingTop = getExtendedPaddingTop();
       int extendedPaddingBottom = getExtendedPaddingBottom();

       final int vspace = mBottom - mTop - compoundPaddingBottom - compoundPaddingTop;
       final int maxScrollY = mLayout.getHeight() - vspace;

       float clipLeft = compoundPaddingLeft + scrollX;
       float clipTop = (scrollY == 0) ? 0 : extendedPaddingTop + scrollY;
       float clipRight = right - left - compoundPaddingRight + scrollX;
       float clipBottom = bottom - top + scrollY -
               ((scrollY == maxScrollY) ? 0 : extendedPaddingBottom);

       

   	
       canvas.clipRect(clipLeft, clipTop, clipRight, clipBottom);

       int voffsetText = 0;
       int voffsetCursor = 0;

//       canvas.translate(compoundPaddingLeft, extendedPaddingTop + voffsetText);
       getLayerMatrix().setTranslate(compoundPaddingLeft, extendedPaddingTop + voffsetText);
       canvas.concat(getLayerMatrix());

       final int cursorOffsetVertical = voffsetCursor - voffsetText;

       Path highlight = getUpdatedHighlightPath();
//       layout.draw(canvas, highlight, mHighlightPaint, cursorOffsetVertical);
       layout.drawSelf(canvas, paint);

       canvas.restore();
   }
   
   
   
   private Path getUpdatedHighlightPath() {
       Path highlight = null;
       Paint highlightPaint = mHighlightPaint;

       final int selStart = getSelectionStart();
       final int selEnd = getSelectionEnd();

       return highlight;
   }
   
   /**
    * Fills in the specified Path with a representation of a highlight
    * between the specified offsets.  This will often be a rectangle
    * or a potentially discontinuous set of rectangles.  If the start
    * and end are the same, the returned path is empty.
    */
   public void getSelectionPath(int start, int end, Path dest) {
       dest.reset();

       if (start == end)
           return;

       if (end < start) {
           int temp = end;
           end = start;
           start = temp;
       }

       int startline = getLineForOffset(start);
       int endline = getLineForOffset(end);

       int top = getLineTop(startline);
       int bottom = getLineBottom(endline);

       if (startline == endline) {
           addSelection(startline, start, end, top, bottom, dest);
       } else {
           final float width = mWidth;

           addSelection(startline, start, getLineEnd(startline),
                        top, getLineBottom(startline), dest);

               dest.addRect(getLineRight(startline), top,
                             width, getLineBottom(startline), Path.Direction.CW);

           for (int i = startline + 1; i < endline; i++) {
               top = getLineTop(i);
               bottom = getLineBottom(i);
               dest.addRect(0, top, width, bottom, Path.Direction.CW);
           }

           top = getLineTop(endline);
           bottom = getLineBottom(endline);

           addSelection(endline, getLineStart(endline), end,
                        top, bottom, dest);

               dest.addRect(0, top, getLineLeft(endline), bottom, Path.Direction.CW);
       }
   }
   
   /**
    * Get the line number on which the specified text offset appears.
    * If you ask for a position before 0, you get 0; if you ask for a position
    * beyond the end of the text, you get the last line.
    */
   public int getLineForOffset(int offset) {
       int high = getLineCount(), low = -1, guess;

       while (high - low > 1) {
           guess = (high + low) / 2;

           if (getLineStart(guess) > offset)
               high = guess;
           else
               low = guess;
       }

       if (low < 0)
           return 0;
       else
           return low;
   }
   
   int getLineCount(){
	   return 1;
   }
   
   int getLineStart(int line){
	   return 20;
   }
   
   public final int getCurrentTextColor() {
       return mCurTextColor;
   }
   
   public int getSelectionStart() {
       return Selection.getSelectionStart(getText());
   }
   
   /**
    * Returns the extended bottom padding of the view, including both the
    * bottom Drawable if any and any extra space to keep more than maxLines
    * of text from showing.  It is only valid to call this after measuring.
    */
   public int getExtendedPaddingBottom() {
       if (mMaxMode != LINES) {
           return getCompoundPaddingBottom();
       }

       if (getLineCount() <= mMaximum) {
           return getCompoundPaddingBottom();
       }

       int top = getCompoundPaddingTop();
       int bottom = getCompoundPaddingBottom();
       int viewht = getHeight() - top - bottom;
       int layoutht = getLineTop(mMaximum);

       if (layoutht >= viewht) {
           return bottom;
       }

       return bottom + (viewht - layoutht) / 2;
   }
   
   
   private static class CharWrapper implements CharSequence, GetChars {
       private char[] mChars;
       private int mStart, mLength;

       public CharWrapper(char[] chars, int start, int len) {
           mChars = chars;
           mStart = start;
           mLength = len;
       }

       /* package */ void set(char[] chars, int start, int len) {
           mChars = chars;
           mStart = start;
           mLength = len;
       }

       public int length() {
           return mLength;
       }

       public char charAt(int off) {
           return mChars[off + mStart];
       }

       @Override
       public String toString() {
           return new String(mChars, mStart, mLength);
       }

       public CharSequence subSequence(int start, int end) {
           if (start < 0 || end < 0 || start > mLength || end > mLength) {
               throw new IndexOutOfBoundsException(start + ", " + end);
           }

           return new String(mChars, start + mStart, end - start);
       }

       public void getChars(int start, int end, char[] buf, int off) {
           if (start < 0 || end < 0 || start > mLength || end > mLength) {
               throw new IndexOutOfBoundsException(start + ", " + end);
           }

           System.arraycopy(mChars, start + mStart, buf, off, end - start);
       }

       public void drawText(Canvas c, int start, int end,
                            float x, float y, Paint p) {
           c.drawText(mChars, start + mStart, end - start, x, y, p);
       }

       public float measureText(int start, int end, Paint p) {
           return p.measureText(mChars, start + mStart, end - start);
       }

       public int getTextWidths(int start, int end, float[] widths, Paint p) {
           return p.getTextWidths(mChars, start + mStart, end - start, widths);
       }
   }
}

