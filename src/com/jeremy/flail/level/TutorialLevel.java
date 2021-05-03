/*    */ package com.jeremy.flail.level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TutorialLevel
/*    */ {
/*    */   public static void start() {
/* 17 */     LevelUtilities.async(() -> {
/*    */           LevelUtilities.loadWorld("tutorial");
/*    */           LevelUtilities.enterActionRegion(112.5F, 4.5F, 1.0F, 2.0F, Level1::start);
/*    */           LevelUtilities.spawnPlayer();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Jeremy\Storage\Desktop\Flail.jar!\com\jeremy\flail\level\TutorialLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */