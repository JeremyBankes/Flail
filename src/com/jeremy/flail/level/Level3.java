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
/*    */ public class Level3
/*    */ {
/*    */   public static void start() {
/* 16 */     LevelUtilities.async(() -> {
/*    */           SplashLayer.getInstance().show(0, 16777215, new String[] { "Level 2 Complete", "Starting Level 3", "Final Level", "Cake... ?" });
/*    */           LevelUtilities.delay(3.0F);
/*    */           LevelUtilities.loadWorld("level3");
/*    */           LevelUtilities.enterActionRegion(83.5F, 7.5F, 3.0F, 2.0F, Ending::start);
/*    */           LevelUtilities.spawnPlayer();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Jeremy\Storage\Desktop\Flail.jar!\com\jeremy\flail\level\Level3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */