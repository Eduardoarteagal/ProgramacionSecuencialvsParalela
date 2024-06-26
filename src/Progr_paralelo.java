
import java.util.Arrays;
import java.util.Random;

public class Progr_paralelo {

    // Función para generar un arreglo de números enteros aleatorios
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    // Implementación del algoritmo Selection Sort
    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    // Implementación del algoritmo Merge Sort
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    public static void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[middle + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
    	long startTime, endTime;
    	startTime = System.currentTimeMillis();
        int[] sizes = {1000, 10000, 100000};

        for (int size : sizes) {
            int[] originalArray = generateRandomArray(size);

            // Copias por valor del arreglo original
            int[] selectionSortArray = Arrays.copyOf(originalArray, originalArray.length);
            int[] mergeSortArray = Arrays.copyOf(originalArray, originalArray.length);

            

            // Hilos para ejecutar los algoritmos en paralelo
            Thread selectionSortThread = new Thread(() -> selectionSort(selectionSortArray));
            Thread mergeSortThread = new Thread(() -> mergeSort(mergeSortArray, 0, mergeSortArray.length - 1));

            // Tiempo total del programa
            

            // Inicio de los hilos
            long selectionSortStartTime = System.currentTimeMillis();
            selectionSortThread.start();
            long mergeSortStartTime = System.currentTimeMillis();
            mergeSortThread.start();

            // Esperar a que los hilos terminen
            selectionSortThread.join();
            long selectionSortEndTime = System.currentTimeMillis();
            mergeSortThread.join();
            long mergeSortEndTime = System.currentTimeMillis();

            endTime = System.currentTimeMillis();

            long totalTime = endTime - startTime;
            long selectionSortTime = selectionSortEndTime - selectionSortStartTime;
            long mergeSortTime = mergeSortEndTime - mergeSortStartTime;

            // Resultados
            System.out.println("Array Size: " + size);
            System.out.println("Selection Sort Time: " + selectionSortTime + " ms");
            System.out.println("Merge Sort Time: " + mergeSortTime + " ms");
            System.out.println("Total Time: " + totalTime + " ms");
            System.out.println();
        }
    }
}
