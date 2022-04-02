import com.tym.tparse.BodyKey;
import com.tym.tparse.HeadKey;
import kotlin.jvm.internal.Intrinsics;

public final class TestData {
   @HeadKey(key = "H.1")
   private  String name;
   @HeadKey(key = "H.2")
   private  String room;

   public TestData() {
   }

   public final String getName() {
      return this.name;
   }



   
   public final String getRoom() {
      return this.room;
   }



   public TestData( String name,  String room) {

      super();
      this.name = name;
      this.room = room;
   }

   
   public final String component1() {
      return this.name;
   }

   
   public final String component2() {
      return this.room;
   }


   
   public final TestData copy( String name,  String room) {

      return new TestData(name, room);
   }

   // $FF: synthetic method
   public static TestData copy$default(TestData var0, String var1, String var2, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.name;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.room;
      }



      return var0.copy(var1, var2);
   }

   
   public String toString() {
      return "TestData(name=" + this.name + ", room=" + this.room + ", list=" + ")";
   }

   public int hashCode() {
      String var10000 = this.name;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      String var10001 = this.room;
      var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;

      return var1 ;
   }

   public boolean equals( Object var1) {
      if (this != var1) {
         if (var1 instanceof TestData) {
            TestData var2 = (TestData)var1;
            if (Intrinsics.areEqual(this.name, var2.name) && Intrinsics.areEqual(this.room, var2.room) ) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}