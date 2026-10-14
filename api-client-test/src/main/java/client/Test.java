package client;

import java.util.List;

import Model.Coche;

public class Test {
	public static void main(String[] args) throws CochesApiException, CochesNotFoundException {
		ApiClient apiClient = new ApiClient("https://cab34902040e42d734d6.free.beeceptor.com/api/coches");
		
		try {
			System.out.println("Test coche con id: 45d94015e17b3da87150");
			Coche coche = apiClient.getCocheById("45d94015e17b3da87150");
			System.out.println(coche);			
			
			System.out.println("---------------------------------------------------------");
			
			System.out.println("Test todos los coches:");
			List<Coche> coches = apiClient.getCochesAll();
			for (Coche c : coches) {
				System.out.println(c);
			}
			
			System.out.println("---------------------------------------------------------");
			
			Coche c = new Coche();
			c.setColor("Azul");
			c.setMarca("Seat");
			c.setModelo("blas");
			c = apiClient.postCoche(c);
			System.out.println(c);
			
		} catch (CochesApiException e) {
			e.printStackTrace();
		} catch (CochesNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}