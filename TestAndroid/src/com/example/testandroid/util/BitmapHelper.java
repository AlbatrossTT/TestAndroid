package com.example.testandroid.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.text.TextUtils;

public final class BitmapHelper {
    
    /**
     * make sure the color data size not more than 5M
     * 
     * @param rect
     * @return
     */
    public static boolean makesureSizeNotTooLarge(Rect rect) {
        final int FIVE_M = 5 * 1024 * 1024;
        if ( rect.width() * rect.height() * 2 > FIVE_M ) {
            // 不能超过5M
            return false;
        }
        return true;
    }

    /**
     * 自适应屏幕大小 得到最大的smapleSize
     * 同时达到此目标： 自动旋转 以适应view的宽高后, 不影响界面显示效果
     * @param vWidth view width
     * @param vHeight view height
     * @param bWidth bitmap width
     * @param bHeight bitmap height
     * @return
     */
    public static int getSampleSizeAutoFitToScreen( int vWidth, int vHeight, int bWidth, int bHeight ) {
        if( vHeight == 0 || vWidth == 0 ) {
            return 1;
        }

        int ratio = Math.max( bWidth / vWidth, bHeight / vHeight );

        int ratioAfterRotate = Math.max( bHeight / vWidth, bWidth / vHeight );

        return Math.min( ratio, ratioAfterRotate );
    }
    
    /**
     * 检测是否可以解析成位图
     * 
     * @param datas
     * @return
     */
    public static boolean verifyBitmap(byte[] datas) {
        return verifyBitmap(new ByteArrayInputStream(datas));
    }

    /**
     * 检测是否可以解析成位图
     * 
     * @param input
     * @return
     */
    public static boolean verifyBitmap(InputStream input) {
        if (input == null) {
            return false;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        input = input instanceof BufferedInputStream ? input
                : new BufferedInputStream(input);
        BitmapFactory.decodeStream(input, null, options);
        FileUtils.closeStream(input);
        return (options.outHeight > 0) && (options.outWidth > 0);
    }

    /**
     * 检测是否可以解析成位图
     * 
     * @param path
     * @return
     */
    public static boolean verifyBitmap(String path) {
        try {
            return verifyBitmap(new FileInputStream(path));
        } catch (final FileNotFoundException e) {
        }
        return false;
    }
    

    /**
     * 相册获取缩略图
     * 
     * @param path
     * @param maxWidth
     * @param maxHeight
     * @param isNeedPicExif 是否考虑PicExif的旋转角度
     * @param isNeedCenterCrop 是否按照maxWidth做CenterCrop
     * @return
     */
    public static Bitmap getBitmapFromFile(String path, int maxWidth, int maxHeight, boolean isNeedPicExif, boolean isNeedCenterCrop) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        file = null;

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        
        options.inJustDecodeBounds = true;

        bitmap = BitmapFactory.decodeFile(path, options);
        float widthRatio = ((float)options.outWidth) / maxWidth;
        float heightRatio = ((float) options.outHeight) / maxHeight;
        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                options.inSampleSize = (int)(widthRatio + 0.5f);
            } else {
                options.inSampleSize = (int)(heightRatio + 0.5f);
            }
        }

        options.inJustDecodeBounds = false;
        options.inInputShareable = true;
        options.inPurgeable = true;
        
        options.inPreferredConfig = Config.RGB_565;
        
//        try {
//            bitmap = BitmapFactory.decodeFile(path, options);
//        } catch (OutOfMemoryError e) {
//            System.gc();
//            e.printStackTrace();
//            options.inSampleSize *= 2;
//            try {
//                bitmap = BitmapFactory.decodeFile(path, options);
//            } catch (OutOfMemoryError e1) {
//                e.printStackTrace();
//                System.gc();
//            }
//        }
        
        bitmap = safeDecodeBimtapFile(path, options);

        if (isNeedCenterCrop) {
            bitmap = getBitmapCropCenter(bitmap, maxWidth, true);
        }
        if (bitmap != null && isNeedPicExif) {
            // exif角度信息
            final int exifRotation = BitmapHelper.getImageRotatation(path);

            // 处理exif角度信息
            Bitmap rotatedThumbBmp = null;
            switch (exifRotation) {
            case 1:
                rotatedThumbBmp = BitmapUtils.createRightRotatedBitmap(bitmap, Config.RGB_565);
                BitmapUtils.recycleBitmap(bitmap);
                break;

            case 2:
                rotatedThumbBmp = BitmapUtils.create180RotatedBitmap(bitmap, Config.RGB_565);
                BitmapUtils.recycleBitmap(bitmap);
                break;

            case 3:
                rotatedThumbBmp = BitmapUtils.createLeftRotatedBitmap(bitmap, Config.RGB_565);
                BitmapUtils.recycleBitmap(bitmap);
                break;

            default:
                rotatedThumbBmp = bitmap;
                break;
            }

            return rotatedThumbBmp;
        }

        return bitmap;
    }
    
    // copy from gallery
    public static Bitmap getBitmapCropCenter(Bitmap bitmap, int size, boolean recycle) {
        if(bitmap == null || bitmap.isRecycled()){
            return bitmap;
        }
        
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int minSide = Math.min(w, h);
        if (w == h && minSide <= size) {
            return bitmap;
        }
        size = Math.min(size, minSide);

        float scale = Math.max((float) size / bitmap.getWidth(), (float) size / bitmap.getHeight());
        Bitmap target = Bitmap.createBitmap(size, size, getConfig(bitmap));
        int width = Math.round(scale * bitmap.getWidth());
        int height = Math.round(scale * bitmap.getHeight());
        Canvas canvas = new Canvas(target);
        canvas.translate((size - width) / 2f, (size - height) / 2f);
        canvas.scale(scale, scale);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (recycle) {
            bitmap.recycle();
        }
        return target;
    }

    private static Bitmap.Config getConfig(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        return config;
    }
    
    public static int getImageRotatation( String imagePath ) {
        if (imagePath == null || imagePath.length() == 0) {
            return 0;
        }

        try {
            Reflection reflection = new Reflection();

            Object exifInterface = getPicExif( reflection, imagePath );
            if (exifInterface != null) {
                final int ORIENTATION_ROTATE_90 = (Integer) reflection.getStaticProperty(
                        "android.media.ExifInterface", "ORIENTATION_ROTATE_90" );
                final int ORIENTATION_ROTATE_180 = (Integer) reflection.getStaticProperty(
                        "android.media.ExifInterface", "ORIENTATION_ROTATE_180" );
                final int ORIENTATION_ROTATE_270 = (Integer) reflection.getStaticProperty(
                        "android.media.ExifInterface", "ORIENTATION_ROTATE_270" );

                String TAG_ORIENTATION = (String) reflection.getStaticProperty(
                        "android.media.ExifInterface", "TAG_ORIENTATION" );

                int tagRotate = -1;
                tagRotate = getTagInt( reflection, TAG_ORIENTATION,
                        exifInterface, -1 );
                if (tagRotate == ORIENTATION_ROTATE_90) {
                    return 1;
                }
                else if (tagRotate == ORIENTATION_ROTATE_180) {
                    return 2;
                }
                else if (tagRotate == ORIENTATION_ROTATE_270) {
                    return 3;
                }
            }
        }
        catch (Exception e) {

        }

        return 0;
    }
    
    public static Object getPicExif(Reflection reflection, String path) {
        Object exifInterface = null;
        try {
            exifInterface = reflection.newInstance(
                    "android.media.ExifInterface", new Object[] { path });
        } catch (IOException e) {
            exifInterface = null;
        } catch (Exception e) {
            exifInterface = null;
        }
        return exifInterface;
    }
    
    /**
     * 获取图片指定
     * @param reflection
     * @param tag
     * @param exif
     * @param defalut
     * @return
     */
    public static int getTagInt(Reflection reflection,String tag,Object exif,int defalut){
        try{
            String value =(String) reflection.invokeMethod(exif, "getAttribute",
                    new Object[] { tag });
            if(value == null){
                return defalut;
            }
            return Integer.valueOf(value);
            
        }catch(Exception e){
        }
        return defalut;
    }

    /**
     * 如果加载时遇到OutOfMemoryError,则将图片加载尺寸缩小一半并重新加载
     * @param bmpFile
     * @param opts 注意：opts.inSampleSize 可能会被改变
     * @return
     */
    public static Bitmap safeDecodeBimtapFile( String bmpFile, BitmapFactory.Options opts ) {
        BitmapFactory.Options optsTmp = opts;
        if ( optsTmp == null ) {
            optsTmp = new BitmapFactory.Options();
            optsTmp.inSampleSize = 1;
        }
        
        Bitmap bmp = null;
        FileInputStream input = null;
        
        final int MAX_TRIAL = 5;
        for( int i = 0; i < MAX_TRIAL; ++i ) {
            try {
                File file = new File(bmpFile);
                file.setLastModified(System.currentTimeMillis());
                input = new FileInputStream( file );
                bmp = BitmapFactory.decodeStream(input, null, opts);
                FileUtils.closeStream(input);
                break;
            }
            catch( OutOfMemoryError e ) {
                e.printStackTrace();
                optsTmp.inSampleSize *= 2;
                FileUtils.closeStream(input);
            }
            catch (FileNotFoundException e) {
                break;
            }
        }
        
        return bmp;
    }
    
    /**
     * 部分机型GPU裁剪有问题，会出现拉伸的情况，这个方法做兼容过滤
     * @return
     */
    private static boolean isCompatibleGPUCrop() {
        if (!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().equals("samsung")) {
            return false;
        }
          return true;
      }
      
      public static Bitmap getCropBitmap(Bitmap source, RectF cropRectF){
          if (isCompatibleGPUCrop()) {
              return getCropBitmapByGPU(source, cropRectF);
          } else {
              return getCropBitmapByCPU(source, cropRectF);
          }
      }
      
      private static Bitmap getCropBitmapByGPU(Bitmap source, RectF cropRectF) {
          Bitmap resultBitmap = Bitmap.createBitmap( (int)cropRectF.width(), (int)cropRectF.height(), Config.ARGB_8888 );
          Canvas cavas = new Canvas( resultBitmap );

          // draw background
          Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
          paint.setColor( Color.WHITE );
          cavas.drawRect( new RectF( 0, 0, cropRectF.width(), cropRectF.height() ), paint );
          
          // draw picture
          Matrix matrix = new Matrix();
          matrix.postTranslate( -cropRectF.left, -cropRectF.top );
          final BitmapShader shader = new BitmapShader( source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP );
          shader.setLocalMatrix( matrix );
          paint.setShader( shader );
          cavas.drawRect( new RectF( 0, 0, cropRectF.width(), cropRectF.height() ), paint );
          if (source != null && !source.isRecycled()) {
              source.recycle();
          }
          
          return resultBitmap;
      }
      
      private static Bitmap getCropBitmapByCPU(Bitmap source, RectF cropRectF) {
          Bitmap resultBitmap = Bitmap.createBitmap( (int)cropRectF.width(), (int)cropRectF.height(), Config.ARGB_8888 );
          Canvas cavas = new Canvas( resultBitmap );

          // draw background
          Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
          paint.setColor( Color.WHITE );
          cavas.drawRect( new RectF( 0, 0, cropRectF.width(), cropRectF.height() ), paint );
          
          Matrix matrix = new Matrix();
          matrix.postTranslate( -cropRectF.left, -cropRectF.top );
          
          cavas.drawBitmap(source, matrix, paint);
          
          if (source != null && !source.isRecycled()) {
              source.recycle();
          }
          
          return resultBitmap;
      }
}
