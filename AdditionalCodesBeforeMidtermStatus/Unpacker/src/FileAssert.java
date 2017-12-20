import java.io.File;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.Files;
import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class FileAssert {

    public static File ResorRootDir;
    public static File CodeRootDir;
    public static Integer Nnode;
    private static Integer MAX;
    private static boolean BringAllToRoot;


    public FileAssert() {
        ResorRootDir = Main.ResorRootDir;
        CodeRootDir = Main.CodeRootDir;
        Nnode = Main.Nnode;
        BringAllToRoot = Main.BringAllToRoot;
    }

    /**
     * Pretty print the directory tree and its file names.
     *
     * @param folder must be a folder.
     * @return
     */

    public static int createDirectoryTree(File folder) throws Exception {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        MAX = 0;
        Integer numFiles = GreatestnumofFiles(folder);
        Nnode = FixNumberofnodes(numFiles, Nnode);
        goDownDirectoryTree(folder);
        return Nnode;
    }


    public static void goDownDirectoryTree(File folder) throws Exception {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        if (Nnode != null && Nnode < 1)
            throw new IllegalArgumentException("Empty Resource Folder");

        for (File file : folder.listFiles()) {
            unzip(file,new File(file.getParent()));
        }

        if (BringAllToRoot){
            for (File file : folder.listFiles()) {

                if (file.isDirectory())
                    goDownDirectoryTree(file);
                else {
                    MoveFile(file,new File(ResorRootDir.getName()+"/"+file.getName()));
                }
            }
        }
        else {
            Integer[] fNn = AssignNumberofnodes(FoldernumofF(folder), Nnode);

            for (File file : folder.listFiles()) {

                if (file.isDirectory())
                    goDownDirectoryTree(file);
                else {
                    MoveFile(file, dirName(file, fNn));
                }
            }
        }
    }

    //create Dir and remove a number from list
    private static File dirName(File file, Integer[] fin) {
        for (int i = 0; i < fin.length; i++) {
            if (fin[i] > 0) {
                fin[i] -= 1;
                return new File(Integer.toString(i) + "/" + file.getPath().replaceFirst(ResorRootDir.toString(),""));
            }
        }
        return null; //should never happen
    }


    private static Integer FoldernumofF(File file) {
        int i = 0;
        for (File f : file.listFiles()) {
            if (!f.isDirectory())
                i += 1;
        }
        return i;
    }

    private static Integer GreatestnumofFiles(File folder) throws IOException {
        Main.clearDIR(folder);
        for (File file : folder.listFiles()) {
            if (file.isDirectory())
                GreatestnumofFiles(file);
            else {

                int temp = folder.listFiles().length;
                if (temp > MAX)
                    MAX = temp;
            }
            //printFile(file, indent + 1, sb);
        }
        return MAX;
    }

    private static int FixNumberofnodes(Integer numFiles, Integer numNodes) {
        if (numNodes > numFiles) {
            return numFiles;
        }
        return numNodes;
    }

    //Without Consulting the size of the files, we are assuming all the files are evenly spraded
    //return List of nodes with number of files in it
    private static Integer[] AssignNumberofnodes(Integer numFiles, Integer numNodes) {

        if (numNodes > numFiles) {
            Integer[] nodesNfile = new Integer[numNodes];
            for (int i = 0; i < numNodes; i++)
                nodesNfile[i] = numFiles;
            return nodesNfile;
        }
        Integer num = Math.floorDiv(numFiles, numNodes);
        Integer[] nodesNfile = new Integer[numNodes];

        for (int i = 0; i < numNodes; i++)
            nodesNfile[i] = num;
        for (int i = 0; i < Math.floorMod(numFiles, numNodes); i++)
            nodesNfile[i] += 1;
        return nodesNfile;
    }

    public static void unzip(File file, File p) throws ZipException {
        if (file.isFile() && file.getName().endsWith(".zip")) {
            ZipFile zipFile = new ZipFile(file.getPath());
            zipFile.extractAll(p.toString());
            file.delete();
        }
    }

    //zip them again to make into image.
    public static void zipinside(File file) throws ZipException {
        if (file.isDirectory() && file.list().length > 0) {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile("Distribute"+file.getName() + ".zip");

            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();

            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            for (File f:file.listFiles()) {
                zipFile.addFile(f,parameters);
            }

            // Add folder to the zip file
           // zipFile.addFolder(file, parameters);
        }
    }

    private static void MoveFile(File src, File tar) {
        if(!src.exists())
            return;
        try{
            if (!BringAllToRoot) {
                File f = new File(tar.getParent());
                if (!f.exists())
                    f.mkdirs();
            }
            Files.move(src.toPath(), tar.toPath() ,REPLACE_EXISTING);
            //unzip(p.toFile(), tar);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Move incomplete");
        }
    }

}