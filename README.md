# TDSPS2  

### List of services (controllers in PigmentsShopDev):  
1. CustomerController:  
   - POST /clients/register?name=&phoneNumber=  
      (register new client)  
   - GET /clients/show  
     (get all registered clients)

2. PigmentController:  
   - GET /pigments/show?id=  
   (get custom pigments of certain user)
   - GET /pigments/effects  
   (get show effects that can be added to a pigment)
   - GET /pigments/colors  
   (get available colors)
   - PUT /pigments/colors?cyanWeight=&magentaWeight=&yellowWeight=&blackWeight=&whiteWeight=  
   (update info about colors)
   - DELETE /pigments/delete?pigmentName=&clientNumber=  
   (delete custom pigment of certain user)
   - POST /pigments/save?name=&cyan=&magenta=&yellow=&black=&white=&clientId=  
   (save custom pigment)
   
3. ShopController:
   - PUT /pillow?pillow=  
   (update shop's pillow)
   - GET /pillow  
   (get shop's pillow)
