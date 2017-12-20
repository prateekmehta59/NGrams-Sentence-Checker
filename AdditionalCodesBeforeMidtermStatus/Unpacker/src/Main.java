
import java.io.*;
import org.apache.commons.io.FileUtils;




//compile with javac -cp zip4j_1.3.2.jar *.java
//run with
//java -cp .:zip4j_1.3.2.jar Main <foldernames>

public class Main {


    public static File ResorRootDir;
    public static File CodeRootDir;
    public static Integer Nnode;
    public static boolean BringAllToRoot = false;
    public static String FileExclusive;


    //Traverse to all folder with DFS

    public static void clearDIR(File rootfolder)throws IOException {
        for (File f : rootfolder.listFiles()) {
            if (f.isFile() && f.isHidden()) {
                f.delete();
            }else if (f.isFile() && FileExclusive !=null && !f.getName().endsWith(FileExclusive)){
                f.delete();
            }
            if (f.isDirectory() && FileUtils.sizeOfDirectory(f) == 0) {
                FileUtils.deleteDirectory(f);
            } else if (f.isDirectory()) {
                clearDIR(f);
            }

        }
    }

    public static void main(String[] args)throws Exception {
//        args = new String[3];
//        args[0] ="Resource";
//        args[1] ="Code";
//        args[2] = "3";
//
//        args = new String[2];
//        args[0] ="8";
//        args[1] =".txt";

        if (args.length  == 2 | args.length  == 1){
            BringAllToRoot = true;
            ResorRootDir = new File(args[0]);
            if (args.length  == 2 ) {
                FileExclusive = args[1];
            }
            FileAssert fileAssert = new FileAssert();
            for (File f : ResorRootDir.listFiles())
                FileAssert.unzip(f, ResorRootDir);
            clearDIR(ResorRootDir);
            fileAssert.goDownDirectoryTree(ResorRootDir);
            clearDIR(ResorRootDir);
            System.exit(0);
        }
        else if (args.length == 3) {
            ResorRootDir = new File(args[0]);
            CodeRootDir = new File(args[1]);
            Nnode = Integer.parseInt(args[2]);
            FileAssert fileAssert = new FileAssert();

            //unzip all the fies in the cod and resource folder, There should not be any zip files inside inner folder
            for (File f : ResorRootDir.listFiles())
                FileAssert.unzip(f, ResorRootDir);
            for (File f : CodeRootDir.listFiles())
                FileAssert.unzip(f, CodeRootDir);
            clearDIR(ResorRootDir);
            clearDIR(CodeRootDir);

            //this will return num of nodes
            int actualnodes = fileAssert.createDirectoryTree(ResorRootDir);


            for (int i = 0; i < actualnodes; i++) {
                File f = new File(String.valueOf(i));
                //include code
                FileUtils.copyDirectory(CodeRootDir, f);
                //Package them into zip
                FileAssert.zipinside(f);
                FileUtils.deleteDirectory(f);
            }

            for (File f : ResorRootDir.listFiles()) {
                if (!f.isDirectory())
                    f.delete();
                FileUtils.deleteDirectory(f);
            }

            for (File f : CodeRootDir.listFiles()) {
                if (!f.isDirectory())
                    f.delete();
                FileUtils.deleteDirectory(f);
            }

            System.exit(actualnodes);
        }else{
            throw new IOException("java -cp \"zip4j_1.3.2.jar:commons-io-2.6.jar:\" Main <Resourcefoldername> " +
                    "<Codingfoldername> <#ofNodes> or java -cp \"zip4j_1.3.2.jar:commons-io-2.6.jar:\" Main <Dir> " +
                    "Optional <Exclusive>");
        }
    }
}
