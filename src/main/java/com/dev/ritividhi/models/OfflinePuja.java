
package com.dev.ritividhi.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document(collection = "offlinepuja")
public class OfflinePuja extends Puja {

    private String description;
    private Samagri samagri;
    private HomePuja homePuja;
    private List<String> god;
    private List<String> mode;  
    
    @DBRef(lazy = false)
    private List<Addon> addons;









//
//
//
//package com.dev.ritividhi.models;
//
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.List;
//
//@Data
//@Document(collection = "offlinepuja")
//public class OfflinePuja {
//
//    @Id
//    private String id;
//
//    private String description; // Specific to Offline Puja
//    private Samagri samagri;
//    private HomePuja homePuja;
//
//    @DBRef(lazy = false)
//    private Puja puja; // Reference to Puja collection
//
//    @DBRef(lazy = false)
//    private List<Addon> addons; // List of Addon references
//}
//
  
}
