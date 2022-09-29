package com.gama.library.services;




import com.gama.library.entities.IssueEntity;
import com.gama.library.models.Issue;
import com.gama.library.persistence.daos.IssueDaoImp;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class IssueServiceImp implements Service<Issue, IssueEntity>{

    private final IssueDaoImp daoImp = new IssueDaoImp();

    private static IssueServiceImp instance;


    public static IssueServiceImp getInstance() {
        if(instance == null){
            return instance = new IssueServiceImp();
        }
        return instance;
    }

    @Override
    public boolean save(Issue issue) {
        IssueEntity issueEntity = new IssueEntity(issue.getBookID(),issue.getMemberID(),issue.getDate(), issue.getRenew_count());
        
        return daoImp.save(issueEntity);
    }

    @Override
    public boolean isExists(String title) {
        return daoImp.isExistsByName(title);
    }

    @Override
    public Optional<Issue> findById(int id) {
        Optional<IssueEntity> issueEntityOptional = daoImp.findById(id);
        if(issueEntityOptional.isPresent()){
            IssueEntity issueEntity = issueEntityOptional.get();

            return Optional.of(new Issue (issueEntity.getBookID(),issueEntity.getMemberID(), issueEntity.getDate(), issueEntity.getRenew_count()
                    ));

        }

        return Optional.empty();
    }

    @Override
    public List<Issue> findAll() {
        List<IssueEntity> list = daoImp.findAll();
        List<Issue> issues = new ArrayList<>();
        if(list != null){
            for (IssueEntity issueEntity : list) {
                issues.add(new Issue (issueEntity.getBookID(),issueEntity.getMemberID(),issueEntity.getDate(),issueEntity.getRenew_count()));
            }
            return issues;
        }
        return null;
    }

    @Override
    public boolean update( Issue issue, int id) {
        IssueEntity issueEntity = new IssueEntity(issue.getBookID(),issue.getMemberID(),issue.getDate(), issue.getRenew_count());
       return daoImp.update(issueEntity,id);

    }

    @Override
    public boolean delete(int id) {
        return daoImp.delete(id);
    }



    @Override
    public List<Issue> findAllByCondition(Predicate<IssueEntity> predicate) {
        List<IssueEntity> issueEntityList = daoImp.findAllByCondition(predicate);
        return null;
    }


}
