package com.flym.fhzk.utils;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class QRCodeUtils {

    /**
     * 成成条形码
     *
     * @param context
     * @param contents
     * @param desiredWidth
     * @param desiredHeight
     * @param displayCode   是否要在条形码下方显示生成的内容。
     * @return
     */
//    public static Bitmap creatBarcode(Context context, String contents,
//                                      int desiredWidth, int desiredHeight, boolean displayCode) {
//        Bitmap ruseltBitmap = null;
//        int marginW = 20;
//        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
//
//        if (displayCode) {
//            Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat,
//                    desiredWidth + 100, desiredHeight);
//            Bitmap codeBitmap = creatCodeBitmap(contents, desiredWidth
//                    , 30, context);
//            ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
//                    0, desiredHeight));
//        } else {
//            ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,
//                    desiredWidth, desiredHeight);
//        }
//
//        return ruseltBitmap;
//    }
//
//
//    /**
//     * 生成条形码
//     *
//     * @param contents
//     * @param format
//     * @param desiredWidth
//     * @param desiredHeight
//     * @return
//     */
//    protected static Bitmap encodeAsBitmap(String contents,
//                                           BarcodeFormat format, int desiredWidth, int desiredHeight) {
//        final int WHITE = 0xFFFFFFFF;
//        final int BLACK = 0xFF000000;
//
//        MultiFormatWriter writer = new MultiFormatWriter();
//        BitMatrix result = null;
//        // 文字编码
//        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        try {
//            result = writer.encode(contents, format, desiredWidth,
//                    desiredHeight, hints);
//        } catch (WriterException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        int width = result.getWidth();
//        int height = result.getHeight();
//        int[] pixels = new int[width * height];
//        // All are 0, or black, by default
//        for (int y = 0; y < height; y++) {
//            int offset = y * width;
//            for (int x = 0; x < width; x++) {
//                if (result.get(x, y))
//                    pixels[y * width + x] = BLACK;
////                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height,
//                Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }
//
//
//    /**
//     * 生成条形码下的数字
//     *
//     * @param contents
//     * @param width
//     * @param height
//     * @param context
//     * @return
//     */
//    protected static Bitmap creatCodeBitmap(String contents, int width,
//                                            int height, Context context) {
//        TextView tv = new TextView(context);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        tv.setLayoutParams(layoutParams);
//        tv.setText(contents);
////        tv.setHeight(height);
//        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//        tv.setWidth(width);
//        tv.setDrawingCacheEnabled(true);
//        tv.setTextColor(Color.BLACK);
//        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
//
//        tv.buildDrawingCache();
//        Bitmap bitmapCode = tv.getDrawingCache();
//        return bitmapCode;
//    }
//
//
//    protected static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
//                                          PointF fromPoint) {
//        if (first == null || second == null || fromPoint == null) {
//            return null;
//        }
//        int marginW = 20;
//        Bitmap newBitmap = Bitmap.createBitmap(
//                first.getWidth(),
//                first.getHeight() + second.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas cv = new Canvas(newBitmap);
//        cv.drawBitmap(first, fromPoint.x, 0, null);
//        cv.drawBitmap(second, 50, fromPoint.y, null);
//        cv.save(Canvas.ALL_SAVE_FLAG);
//        cv.restore();
//
//        return newBitmap;
//    }

}
