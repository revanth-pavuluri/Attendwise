package com.ams.app.specification;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Boolean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import com.ams.app.dto.SearchrequestDTO;
import com.ams.app.dto.RequestDTO;

@Service
public class CommonFilterSpecification<T> {

     public Specification<T> getSearchSpecification(List<SearchrequestDTO> searchreqs, RequestDTO.Operators globalOperator) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            List<String> cols = getColumnNames(root);
            for(SearchrequestDTO requestDto : searchreqs){
                Expression<String> roots;
                
                if(cols.indexOf(requestDto.getColumn()) == -1){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                roots = root.get(requestDto.getColumn());

                switch (requestDto.getOperation()){

                    case EQUAL:
                        Predicate equal;
                        if (roots.getJavaType().equals(Boolean.class)){
                            equal = criteriaBuilder.equal(roots, Boolean.parseBoolean(requestDto.getValue()));

                        }else if(roots.getJavaType().isEnum()){
                            Class<?> c = ((Path<?>)roots).getModel().getBindableJavaType();
                            Object val = "";
                            try {
                                val = Enum.valueOf((Class<Enum>) c, requestDto.getValue());
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            
                            equal = criteriaBuilder.equal(roots, val);
                        }
                        else{
                            equal = criteriaBuilder.equal(roots, requestDto.getValue());
                        }
                        predicates.add(equal);
                        
                        break;

                    case LIKE:
                        Predicate like = criteriaBuilder.like(roots, "%"+requestDto.getValue()+"%");
                        predicates.add(like);
                        break;
                    case STARTS:
                        Predicate starts = criteriaBuilder.like(roots, requestDto.getValue()+"%");
                        predicates.add(starts);
                        break;
                    case ENDS:
                        Predicate ends = criteriaBuilder.like(roots, "%"+requestDto.getValue());
                        predicates.add(ends);
                        break;

                    case IN:
                        String[] split = requestDto.getValue().split(",");
                        Predicate in = roots.in(Arrays.asList(split));
                        predicates.add(in);
                        break;

                    case GREATER_THAN:
                        Predicate greaterThan = criteriaBuilder.greaterThan(roots, requestDto.getValue());
                        predicates.add(greaterThan);
                        break;

                    case LESS_THAN:
                        Predicate lessThan = criteriaBuilder.lessThan(roots, requestDto.getValue());
                        predicates.add(lessThan);
                        break;
                    case ASC:
                        query.orderBy(criteriaBuilder.asc(roots)); 
                        predicates.add(criteriaBuilder.conjunction());
                        break;
                    case DESC:
                        query.orderBy(criteriaBuilder.desc(roots)); 
                        predicates.add(criteriaBuilder.conjunction());
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + requestDto.getOperation());
                }

            }

            if(globalOperator.equals(RequestDTO.Operators.AND)) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }else if(globalOperator.equals(RequestDTO.Operators.OR)){
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }else{
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
    private <T> List<String> getColumnNames(Root <T> root) {
        EntityType<T> entityType = root.getModel();
        List<String> columnNames = new ArrayList<>();
        for (Attribute<T, ?> attribute : entityType.getDeclaredAttributes()) {
            columnNames.add(attribute.getName());
        }
        return columnNames;
    }
}