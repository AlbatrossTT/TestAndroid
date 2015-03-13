//package com.example.testandroid.util;
//
//import android.graphics.Bitmap;
//import android.media.MediaMetadataRetriever;
//
//public class Util {
//	
//	public Bitmap createVideoThumbnail(String filePath) {
//        Bitmap bitmap = null;
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        try {
//            retriever.setMode(MediaMetadataRetriever.MODE_CAPTURE_FRAME_ONLY);
//            retriever.setDataSource(filePath);
//            bitmap = retriever.captureFrame();
//        } catch(IllegalArgumentException ex) {
//            // Assume this is a corrupt video file
//        } catch (RuntimeException ex) {
//            // Assume this is a corrupt video file.
//        } finally {
//            try {
//                retriever.release();
//            } catch (RuntimeException ex) {
//                // Ignore failures while cleaning up.
//            }
//        }
//        return bitmap;
//    }
//
////	public static Bitmap BitmapcreateVideoThumbnail(String filePath) {
////
////		Bitmap bitmap = null;
////		MediaMetadataRetriever retriever= new MediaMetadataRetriever();
////		try {
////		retriever.setMode(MediaMetadataRetriever.MODE_CAPTURE_FRAME_ONLY);
////		retriever.setDataSource(filePath);
////		bitmap =retriever.captureFrame();
////		} catch(IllegalArgumentExceptionex) {
////		// Assume this is a corruptvideo file
////		} catch (RuntimeException ex){
////		// Assume this is a corruptvideo file.
////		} finally {
////		try {
////		retriever.release();
////		} catch (RuntimeException ex){
////		// Ignore failures whilecleaning up.
////		}
////		}
////		return bitmap;
////
////	}
//}
