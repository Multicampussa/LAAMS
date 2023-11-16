from django.db import models


# Create your models here.
class ComparisonLog(models.Model):
    no = models.AutoField(primary_key=True)
    examinee_name = models.CharField(max_length=20)
    examinee_no = models.CharField(max_length=20)
    existing_photo = models.CharField(max_length=300)
    new_photo = models.CharField(max_length=300)

    class Meta:
        db_table = 'comparison_log'

