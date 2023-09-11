

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GitOperations {

    public void GitPull(String gitUrl, String localClonePath) {

        System.out.println(getTime() + ". git pull start");

        CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(new File(localClonePath));

        // (선택사항) 인증 정보 설정 (private 저장소의 경우)
        UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("hanbyul6513@gmail.com", "trdfvc6518!");
        cloneCommand.setCredentialsProvider(credentialsProvider);

        try {
            // Git 저장소 복제 실행
            Git git = cloneCommand.call();
            System.out.println(getTime() + ". 프로젝트 복제 성공");
            git.close();
        } catch (Exception e) {
            System.out.println(getTime() + ". 프로젝트 복제 실패 : " + e.getMessage());
        }
    }

    public void getRepositoryCodeLine(String localRepositoryPath) {
        String repositoryPath = localRepositoryPath;

        try {
            Repository repository = Git.open(new File(repositoryPath)).getRepository();

            // HEAD 커밋을 얻어옴
            ObjectId head = repository.resolve("HEAD");

            if (head != null) {
                Git git = new Git(repository);
                RevWalk revWalk = new RevWalk(repository);

                // HEAD 커밋의 트리 얻기
                RevTree tree = revWalk.parseTree(head);

                // 트리워커 초기화
                TreeWalk treeWalk = new TreeWalk(repository);
                treeWalk.addTree(tree);
                treeWalk.setRecursive(true);

                // 트리에 있는 모든 파일 순회
                while (treeWalk.next()) {
                    ObjectId objectId = treeWalk.getObjectId(0);
                    ObjectLoader loader = repository.open(objectId);

                    // 파일 이름과 라인 수 출력
                    String fileName = treeWalk.getPathString();
                    String fileContent = new String(loader.getBytes());
                    String[] lines = fileContent.split("\n");
                    int lineCount = lines.length;
                    System.out.println("파일: " + fileName + ", 라인 수: " + lineCount);
                }

            } else {
                System.err.println("HEAD 커밋을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
