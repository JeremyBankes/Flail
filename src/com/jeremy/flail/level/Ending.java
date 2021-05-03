/*    */ package com.jeremy.flail.level;
/*    */ 
/*    */ import com.jeremy.flail.ui.EndingLayer;
/*    */ import com.jeremy.flail.ui.Layer;
/*    */ import com.jeremy.flail.ui.Overlay;
/*    */ import com.jeremy.flail.ui.SplashLayer;
/*    */ 
/*    */ 
/*    */ public class Ending
/*    */ {
/* 11 */   private static final String[] STORY = new String[] { "Level 3 Complete", "After much triumph...", "...and many a tribulation...", "...your hard work...", 
/* 12 */       "...pays off...", "Cake." };
/*    */   
/*    */   public static void start() {
/* 15 */     LevelUtilities.async(() -> {
/*    */           Overlay.getInstance().addLayer((Layer)EndingLayer.getInstance());
/*    */           SplashLayer.getInstance().show(0, 16777215, STORY);
/*    */           LevelUtilities.delay(3.0F);
/*    */           EndingLayer.getInstance().show();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Jeremy\Storage\Desktop\Flail.jar!\com\jeremy\flail\level\Ending.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */