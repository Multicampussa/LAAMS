from rest_framework import serializers
from .models import ComparisonLog


class ComparisonLogSerializer(serializers.ModelSerializer):
    class Meta:
        model = ComparisonLog
        exclude = ['no']