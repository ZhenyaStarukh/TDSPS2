# TDSPS2  

### List of services (controllers in PigmentsShopDev):  
1. CustomerController:  
   - /clients/register?name=&phoneNumber=  
      (register new client)  
   - /clients/show  
     (get all registered clients)

2. PigmentController:  
   - /pigments/show?id=  
   (get custom pigments of certain user)
   - /pigments/effects  
   (get show effects that can be added to a pigment)
   - /pigments/colors  
   (get available colors)
   - /pigments/colors?weight 
   (update info about colors)
   - /pigments/?pigmentName=&clientNumber=  
   (delete custom pigment of certain user)
   - /pigments/save?name=&array=&clientId=  
   (save custom pigment)
   
3. ShopController:
   - /pillow?pillow=  
   (update shop's pillow)
   - /pillow  
   (get shop's pillow)
