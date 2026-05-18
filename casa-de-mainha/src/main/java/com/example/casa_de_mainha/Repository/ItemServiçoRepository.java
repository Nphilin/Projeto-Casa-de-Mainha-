
package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.ItemServiço;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface ItemServiçoRepository extends CrudRepository<ItemServiço, Long> {

    // List<ItemServiço> findByServicoId(Long id);

}
