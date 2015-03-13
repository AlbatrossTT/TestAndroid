package com.example.testandroid.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;

public class MaskBitmapUtil {
    
    public static Bitmap CreateMaskedBitmap(Drawable bemasked, Bitmap mask, int width, int height) {
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint pen = new Paint();
        pen.setFilterBitmap(false);
        pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        bemasked.setBounds(0, 0, width, height);
        bemasked.draw(canvas);// dst
        canvas.drawBitmap(mask, new Rect(0, 0, mask.getWidth(), mask.getHeight()), // src
                new Rect(0, 0, width, height), // dst
                pen);
        return bmp;
    }

    public static Bitmap CreateMaskedBitmap(Bitmap bemasked, Bitmap mask, int w, int h) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint pen = new Paint();
        pen.setFilterBitmap(false);
        pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        Rect _src = new Rect(0, 0, bemasked.getWidth(), bemasked.getHeight());
        Rect _dst = new Rect(0, 0, w, h);
        canvas.drawBitmap(bemasked, _src, // src
                _dst, // dst
                null);
        canvas.drawBitmap(mask, new Rect(0, 0, mask.getWidth(), mask.getHeight()), _dst, pen);
        return bmp;
    }

    public static Bitmap CreateMaskedBitmap(Bitmap bemasked, Drawable mask, int w, int h) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);
        Paint pen = new Paint();
        pen.setFilterBitmap(false);
        pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        pen.setColor(0xffffffff);
        canvas.drawBitmap(bemasked, new Rect(0, 0, bemasked.getWidth(), bemasked.getHeight()), // srd
                new Rect(0, 0, w, h), null);

        mask.setBounds(0, 0, w, h);
        Bitmap bmask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas bcanvas = new Canvas(bmask);
        mask.draw(bcanvas);

        canvas.drawBitmap(bmask, 0, 0, pen);
        bmask.recycle();

        return bmp;
    }

    public static Bitmap CreateMaskedBitmapCrop(Bitmap bemasked, Drawable mask, int w, int h) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);
        Paint pen = new Paint();
        pen.setFilterBitmap(false);
        pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        pen.setColor(0xffffffff);
        canvas.drawBitmap(bemasked, new Rect(0, 0, w, h), // srd
                new Rect(0, 0, w, h), null);

        mask.setBounds(0, 0, w, h);
        Bitmap bmask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas bcanvas = new Canvas(bmask);
        mask.draw(bcanvas);

        canvas.drawBitmap(bmask, 0, 0, pen);
        bmask.recycle();

        return bmp;
    }

    public static Bitmap CreateMaskedBitmap(Bitmap bemasked, int w, int h, int radius) {
        // Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //
        // Canvas canvas = new Canvas(bmp);
        // Paint pen = new Paint();
        // pen.setFilterBitmap(false);
        // pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // pen.setColor(0xffffffff);
        // canvas.drawBitmap(bemasked,
        // new Rect(0, 0, bemasked.getWidth(),bemasked.getHeight()), //srd
        // new Rect(0, 0, w, h),
        // null);
        //
        // mask.setBounds(0, 0, w, h);
        // Bitmap bmask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        // Canvas bcanvas = new Canvas(bmask);
        // mask.draw(bcanvas);
        //
        // canvas.drawBitmap(bmask, 0, 0, pen);
        // bmask.recycle();
        //
        // return bmp;

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawARGB(0, 0, 0, 0);
        Paint pen = new Paint();
        pen.setColor(0xffffffff);
        pen.setAntiAlias(true);
        RectF _rect = new RectF(0, 0, w, h);
        canvas.drawRoundRect(_rect, radius, radius, pen);
        pen.setFilterBitmap(false);
        pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bemasked, null,// new
                                         // Rect(0,0,bemasked.getWidth(),bemasked.getHeight()),
                                         // //src
                _rect, // dst
                pen);
        return bmp;
    }

    public static Bitmap CreateMaskedCircleBitmap(Bitmap bemasked, int diameter, int bgStrokeWidth, int colorBG, int colorBorder) {

        Bitmap bmp = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawARGB(0, 0, 0, 0);
        Paint pen = new Paint();
        pen.setColor(0xffffffff);
        // pen.setStyle(Style.FILL_AND_STROKE);
        pen.setAntiAlias(true);
        RectF _rect = new RectF(0, 0, diameter, diameter);
        // canvas.drawRoundRect(_rect, radius, radius, pen);
        canvas.drawCircle(diameter >> 1, diameter >> 1, diameter >> 1, pen);

        Bitmap bgBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas bgcanvas = new Canvas(bgBitmap);
        bgcanvas.drawColor(colorBG);
        bgcanvas.drawBitmap(bemasked, null, _rect, null);

        Paint paint = new Paint();
        paint.setColor(colorBorder);
        paint.setStrokeWidth(bgStrokeWidth);
        paint.setStyle(Style.STROKE);
        bgcanvas.drawCircle(diameter >> 1, diameter >> 1, diameter >> 1, paint);

        pen.setFilterBitmap(false);
        pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bgBitmap, null,// new
                                         // Rect(0,0,bemasked.getWidth(),bemasked.getHeight()),
                                         // //src
                _rect, // dst
                pen);

        bgBitmap.recycle();

        return bmp;
    }
}
