/*@author Samir Hasanov */
package repository;

import java.util.List;

public interface Repository<O> {
    void add(O o);
    void update(O o);
    void remove(O o);
    List<O> findAll();
}
