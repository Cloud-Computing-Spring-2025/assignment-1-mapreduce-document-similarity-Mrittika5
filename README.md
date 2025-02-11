[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18028338&assignment_repo_type=AssignmentRepo)
### **📌 Document Similarity Using Hadoop MapReduce**  
The Mapper processes each document by extracting unique words and emitting key-value pairs, where the key is a document pair, and the value is a word they share. It generates intermediate outputs that help track word overlaps between documents.
The Reducer receives word-based groupings of document pairs and counts the shared words. It then computes the Jaccard Similarity using the formula. Finally, it outputs the similarity score for each document pair.
### **📥 Example Input**  

You will be given multiple text documents. Each document will contain several words. Your task is to compute the **Jaccard Similarity** between all pairs of documents based on the set of words they contain.  

#### **Example Documents**  

##### **doc1.txt**  
```
hadoop is a distributed system
```

##### **doc2.txt**  
```
hadoop is used for big data processing
```

##### **doc3.txt**  
```
big data is important for analysis
```

---

# 📏 Jaccard Similarity Calculator

## Overview

The Jaccard Similarity is a statistic used to gauge the similarity and diversity of sample sets. It is defined as the size of the intersection divided by the size of the union of two sets.

## Formula

The Jaccard Similarity between two sets A and B is calculated as:

```
Jaccard Similarity = |A ∩ B| / |A ∪ B|
```

Where:
- `|A ∩ B|` is the number of words common to both documents
- `|A ∪ B|` is the total number of unique words in both documents

## Example Calculation

Consider two documents:
 
**doc1.txt words**: `{hadoop, is, a, distributed, system}`
**doc2.txt words**: `{hadoop, is, used, for, big, data, processing}`

- Common words: `{hadoop, is}`
- Total unique words: `{hadoop, is, a, distributed, system, used, for, big, data, processing}`

Jaccard Similarity calculation:
```
|A ∩ B| = 2 (common words)
|A ∪ B| = 10 (total unique words)

Jaccard Similarity = 2/10 = 0.2 or 20%
```

## Use Cases

Jaccard Similarity is commonly used in:
- Document similarity detection
- Plagiarism checking
- Recommendation systems
- Clustering algorithms

## Implementation Notes

When computing similarity for multiple documents:
- Compare each document pair
- Output pairs similarity score

### **📤 Expected Output**  

The output should show the Jaccard Similarity between document pairs in the following format:  
```
(doc1, doc2) -> 60%  
(doc2, doc3) -> 50%  
```

set up: 

### 1. **Start the Hadoop Cluster**

Run the following command to start the Hadoop cluster:

```bash
docker compose up -d
```

### 2. **Build the Code**

Build the code using Maven:

```bash
mvn install
```

### 3. **Move JAR File to input forlder
Move the generated JAR file to a input folder for easy access:
I moved it manually 
```

### 4. **Copy JAR to Docker Container**

Copy the JAR file to the Hadoop ResourceManager container:

```bash
docker cp input/DocumentSimilarity-0.0.1-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. **Move Dataset to Docker Container**

Copy the dataset to the Hadoop ResourceManager container:

```bash
 docker cp input/data.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 6. **Connect to Docker Container**

Access the Hadoop ResourceManager container:

```bash
docker exec -it resourcemanager /bin/bash
```

Navigate to the Hadoop directory:

```bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 7. **Set Up HDFS**

Create a folder in HDFS for the input dataset:

```bash
 hadoop fs -mkdir -p /input/dataset2
```

Copy the input dataset to the HDFS folder:

```bash
hadoop fs -put ./data.txt /input/dataset2
```

### 8. **Execute the MapReduce Job**

Run your MapReduce job using the following command:

```bash
hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/DocumentSimilarity-0.0.1-SNAPSHOT.jar   com.example.controller.DocumentSimilarityDriver /input/dataset2/data.txt  /output3
```

### 9. **View the Output**

To view the output of your MapReduce job, use:

```bash
hadoop fs -cat /output3/*
```

### 10. **Copy Output from HDFS to Local OS**

To copy the output from HDFS to your local machine:

1. Use the following command to copy from HDFS:
    ```bash
  hdfs dfs -get /output3 /opt/hadoop-3.2.1/share/hadoop/mapreduce/
    ```

2. use Docker to copy from the container to your local machine:
   ```bash
   exit 
   ```
    ```bash
  docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output3/ output
    ```