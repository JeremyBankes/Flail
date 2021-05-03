/*    */ package com.jeremy.flail.level;
/*    */ 
/*    */ import com.jeremy.flail.ui.SplashLayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Level1
/*    */ {
/*    */   public static void start() {
/* 16 */     LevelUtilities.async(() -> {
/*    */           SplashLayer.getInstance().show(0, 16777215, new String[] { "Tutorial Complete", "Starting Level 1" });
/*    */           LevelUtilities.delay(3.0F);
/*    */           LevelUtilities.loadWorld("level1");
/*    */           LevelUtilities.enterActionRegion(93.5F, 9.5F, 1.0F, 2.0F, Level2::start);
/*    */           LevelUtilities.spawnPlayer();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Jeremy\Storage\Desktop\Flail.jar!\com\jeremy\flail\level\Level1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */