FROM microsoft/dotnet:sdk AS build-env
WORKDIR /app

# Copy csproj and restore as distinct layers
COPY src/api/src/*/*.csproj ./
RUN for file in $(ls *.csproj); do mkdir -p src/api/src/${file%.*}/ && mv $file src/api/src/${file%.*}/; done
COPY shared/*/*.csproj ./
RUN for file in $(ls *.csproj); do mkdir -p shared/${file%.*}/ && mv $file shared/${file%.*}/; done
WORKDIR /app/src/api/src/Interviewd/
RUN dotnet restore

# Copy everything else and build
WORKDIR /app/
COPY ./ ./
WORKDIR /app/src/api/src/Interviewd/
RUN dotnet publish -c Release -o out

# Build runtime image
FROM microsoft/dotnet:aspnetcore-runtime
WORKDIR /app
COPY --from=build-env /app/src/api/src/Interviewd/out .
ENTRYPOINT ["dotnet", "Interviewd.dll"]