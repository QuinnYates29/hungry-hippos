// /**
//  * Test file for CupboardController.java class
//  * @author Quinn, Ilia
//  * Specific test functions have authors above them for code submission for
//  * Unit Testing - Individual Assignment
//  */

// package com.ufund.api.ufundapi;

// import java.io.IOException;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import static org.mockito.Mockito.when;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.ufund.api.ufundapi.controller.CupboardController;
// import com.ufund.api.ufundapi.model.Need;
// import com.ufund.api.ufundapi.persistence.CupboardDAO;

// class CupboardControllerTest {

//     private CupboardController controller;
//     private CupboardDAO mockDao;

//     @BeforeEach
//     void setUp() {
//         mockDao = Mockito.mock(CupboardDAO.class);
//         controller = new CupboardController(mockDao);
//     }

//     // =========================
//     // Tests for getNeeds()
//     // Author: Quinn Yates
//     // =========================

//     @Test
// public void testGetNeed_Ok() throws IOException {
//     Need need = new Need(1, "Tuna", "Food", 3.00, 10);
//     when(mockDao.getNeed(1)).thenReturn(need);

//     ResponseEntity<Need> response = controller.getNeed(1);

//     assertEquals(HttpStatus.OK, response.getStatusCode());
//     assertEquals(need, response.getBody());
// }

// @Test
// public void testGetNeed_NotFound() throws IOException {
//     when(mockDao.getNeed(1)).thenReturn(null);

//     ResponseEntity<Need> response = controller.getNeed(1);

//     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// }

// // @Test
// // public void testGetNeed_InternalServerError() throws IOException {
// //     when(mockDao.getNeed(1)).thenThrow(new IOException());

// //     ResponseEntity<Need> response = controller.getNeed(1);

// // <<<<<<< HEAD
// //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
// // }
// // =======
// //         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// //     }

//     @Test
//     public void testGetNeeds_InternalServerError() throws IOException {
//         when(mockDao.getNeeds()).thenThrow(new IOException());

//         ResponseEntity<Need[]> response = controller.getNeeds();

//         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//     }

//     // =========================
//     // Tests for deleteNeed()
//     // Author: Quinn Yates
//     // =========================

//     @Test
//     public void testDeleteNeed_Ok() throws IOException {
//         when(mockDao.deleteNeed(1)).thenReturn(true);

//         ResponseEntity<Need> response = controller.deleteNeed(1);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//     }

//     @Test
//     public void testDeleteNeed_NotFound() throws IOException {
//         when(mockDao.deleteNeed(1)).thenReturn(false);

//         ResponseEntity<Need> response = controller.deleteNeed(1);

//         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//     }

//     @Test
//     public void testDeleteNeed_InternalServerError() throws IOException {
//         when(mockDao.deleteNeed(1)).thenThrow(new IOException());

//         ResponseEntity<Need> response = controller.deleteNeed(1);

//         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//     }

//     // =========================
//     // Tests for searchNeeds():
//     // Author: Ilia
//     // =========================
//     @Test
//     public void testSearchNeedsFullName_Ok() throws IOException {
//         Need[] mockNeeds = { 
//         new Need(1, "Grass", "Food", 0, 10), 
//         new Need(2, "Water", "Drink", 0, 20) 
//         };
//         when(mockDao.getNeeds()).thenReturn(mockNeeds);
//         ResponseEntity<Need[]> response = controller.searchNeeds("Grass");
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertNotNull(response.getBody());
//         assertEquals(1, response.getBody().length);
//         assertEquals("Grass", response.getBody()[0].getName());
//     }

//     @Test
//     public void testSearchNeedsPartialName_Ok() throws IOException {
//         Need[] mockNeeds = { 
//             new Need(1, "Grass", "Food", 0, 10), 
//             new Need(2, "Water", "Drink", 0, 20) 
//         };
//         when(mockDao.getNeeds()).thenReturn(mockNeeds);
//         ResponseEntity<Need[]> response = controller.searchNeeds("wat");
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertNotNull(response.getBody());
//         assertEquals(1, response.getBody().length);
//         assertEquals("Water", response.getBody()[0].getName());
//     }
    
//     @Test
//     public void testSearchNeeds_NotFound() throws IOException {
//         Need[] mockNeeds = {
//             new Need(1, "Grass", "Food", 0, 10),
//             new Need(2, "Water", "Drink", 0, 20)
//         };
//         when(mockDao.getNeeds()).thenReturn(mockNeeds);
//         ResponseEntity<Need[]> response = controller.searchNeeds("Cookie");
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertNotNull(response.getBody());
//         assertEquals(0, response.getBody().length);
//     }

//     @Test
//     public void testSearchNeeds_InternalServerError() throws IOException {
//         when(mockDao.getNeeds()).thenThrow(new IOException());
//         ResponseEntity<Need[]> response = controller.searchNeeds("Grass");
//         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//     }
// <<<<<<< HEAD

// >>>>>>> d68a510153daab14890b41725d93382a791afe92
// =======
// >>>>>>> f9bd0a59d53d5038ec1057d41dafbde01446be1f
// }