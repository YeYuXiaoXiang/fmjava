package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.specification.SpecificationDao;
import com.fmjava.core.dao.specification.SpecificationOptionDao;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.SpecEntity;
import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.pojo.specification.SpecificationOption;
import com.fmjava.core.pojo.specification.SpecificationOptionQuery;
import com.fmjava.core.pojo.specification.SpecificationQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class SpecServiceImpl implements SpecService{
    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;

    @Override
    public PageResult findPage(Integer page, Integer pageSize, Specification specification) {
        PageHelper.startPage(page, pageSize);
        SpecificationQuery specificationQuery = new SpecificationQuery();

        if (specification != null){
            SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
            if (specification.getSpecName() != null && !"".equals(specification.getSpecName())){
                criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
            }
        }
        Page<Specification> specifications = (Page<Specification>)specificationDao.selectByExample(specificationQuery);
        PageResult pageResult = new PageResult(specifications.getTotal(), specifications.getResult());

        return pageResult;
    }

    @Override
    public void save(SpecEntity specEntity) {
        specificationDao.insertSelective(specEntity.getSpec());
        for (SpecificationOption specificationOption : specEntity.getSpecOption()) {
            specificationOption.setSpecId(specEntity.getSpec().getId());
            specificationOptionDao.insertSelective(specificationOption);

        }

    }

    @Override
    public SpecEntity findSpecWithId(Long id) {
        Specification specification = specificationDao.selectByPrimaryKey(id);

        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(specification.getId());
        List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(specificationOptionQuery);

        SpecEntity specEntity = new SpecEntity();
        specEntity.setSpec(specification);
        specEntity.setSpecOption(specificationOptions);
        return specEntity;

    }

    @Override
    public void update(SpecEntity specEntity) {
        specificationDao.updateByPrimaryKeySelective(specEntity.getSpec());

        SpecificationOptionQuery query = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = query.createCriteria();
        criteria.andSpecIdEqualTo(specEntity.getSpec().getId());
        specificationOptionDao.deleteByExample(query);

        for (SpecificationOption specificationOption : specEntity.getSpecOption()) {
            specificationOption.setSpecId(specEntity.getSpec().getId());
            specificationOptionDao.insertSelective(specificationOption);

        }
    }

    @Override
    public void delete(Long[] idx) {
        if (idx != null){
            for (Long id : idx) {
                specificationDao.deleteByPrimaryKey(id);

                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();

                criteria.andSpecIdEqualTo(id);
                specificationOptionDao.deleteByExample(query);

            }
        }
    }

    @Override
    public List<Map> selectOptionList() {

        return specificationDao.selectOptionList();
    }
}
