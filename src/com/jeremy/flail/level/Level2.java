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
/*    */ public class Level2
/*    */ {
/*    */   public static void start() {
/* 16 */     LevelUtilities.async(() -> {
/*    */           SplashLayer.getInstance().show(0, 16777215, new String[] { "Level 1 Complete", "Starting Level 2" });
/*    */           LevelUtilities.delay(3.0F);
/*    */           LevelUtilities.loadWorld("level2");
/*    */           LevelUtilities.enterActionRegion(93.5F, 13.5F, 1.0F, 2.0F, Level3::start);
/*    */           LevelUtilities.spawnPlayer();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Jeremy\Storage\Desktop\Flail.jar!\com\jeremy\flail\level\Level2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */